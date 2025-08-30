package com.cms.module.ocr.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cms.module.ocr.dto.ReceiptInfoListItem;
import com.cms.module.ocr.entity.ReceiptInfo;
import com.cms.module.ocr.mapper.ReceiptInfoMapper;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.ImageAnnotatorSettings;
import com.google.cloud.vision.v1.ImageContext;
import com.google.protobuf.ByteString;

import lombok.RequiredArgsConstructor;

/**
 * ================================================================
 * OCR 読み取り・保存・更新・削除・エクスポート 実装クラス
 *
 * - Google Vision API を利用した OCR 読み取り
 * - 保存（画像＋項目／バッチ）
 * - 更新（項目のみ）
 * - 削除（保存取消）
 * - Excel 出力
 *
 * 画像保存先は application.yml の設定値に従う。
 * ================================================================
 */
@Service
@RequiredArgsConstructor
public class OcrReadServiceImpl implements OcrReadService {

    private final ReceiptInfoMapper receiptInfoMapper;

    /** Google 認証キー（application.yml or 環境変数から注入） */
    @Value("${ocr.google.key-path:${GOOGLE_OCR_KEY_PATH:}}")
    private String googleKeyPath;

    /** 画像保存ルート（例: C:/data/ocr_image_save/） */
    @Value("${ocr.upload-dir:C:/Users/Micheal/Desktop/ocr_image_save/}")
    private String uploadRoot;

    /** 公開パスのプレフィックス（例: /uploads/ → uploadRoot にマップ） */
    @Value("${ocr.public-prefix:/uploads/}")
    private String publicPrefix;

    // ------------------------------------------------------------
    // 1) OCR 読み取り
    // ------------------------------------------------------------
    @Override
    public Map<String, Object> parse(MultipartFile file) throws Exception {
        if (googleKeyPath == null || googleKeyPath.isBlank()) {
            throw new IllegalStateException("Google OCR key path not configured.");
        }

        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(googleKeyPath));
        ImageAnnotatorSettings settings = ImageAnnotatorSettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                .build();

        ByteString imgBytes = ByteString.readFrom(file.getInputStream());
        Image img = Image.newBuilder().setContent(imgBytes).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.DOCUMENT_TEXT_DETECTION).build();
        ImageContext context = ImageContext.newBuilder().addLanguageHints("ja").build();

        AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                .addFeatures(feat).setImage(img).setImageContext(context).build();

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create(settings)) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(List.of(request));
            AnnotateImageResponse res = response.getResponses(0);

            if (res.hasError()) throw new RuntimeException("OCR Error: " + res.getError().getMessage());

            String fullText = res.getFullTextAnnotation().getText();
            Map<String, Object> result = new HashMap<>();
            result.put("issuer", extractIssuer(fullText));
            result.put("number", extractRegistrationNumber(fullText));
            result.put("date",   extractDateString(fullText));
            result.put("amount", extractAmountString(fullText));
            result.put("full_text", fullText);
            return result;
        }
    }

    // ------------------------------------------------------------
    // 2) 保存（新規／バッチ）
    // ------------------------------------------------------------
    @Override
    public Long saveOne(String issuer, String number, String date, String amount,
                        String fullText, MultipartFile file, String userId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("issuer", issuer);
        map.put("number", number == null ? "" : number);
        map.put("date",   date);
        map.put("amount", amount);
        map.put("full_text", fullText == null ? "" : fullText);
        return doSave(map, file, userId);
    }

    @Override
    public List<Long> saveBatch(List<MultipartFile> files, List<String> issuer, List<String> number,
                                List<String> date, List<String> amount, List<String> fullText,
                                String userId) throws Exception {
        int n = files.size();
        List<Long> ids = new ArrayList<>(Collections.nCopies(n, null));

        for (int i = 0; i < n; i++) {
            try {
                Map<String, Object> map = new HashMap<>();
                map.put("issuer", safeGet(issuer, i));
                map.put("number", safeGet(number, i));
                map.put("date",   safeGet(date, i));
                map.put("amount", safeGet(amount, i));
                map.put("full_text", safeGet(fullText, i));
                Long id = doSave(map, files.get(i), userId);
                ids.set(i, id);
            } catch (Exception ignore) {
                // 失敗は null のまま
            }
        }
        return ids;
    }

    /** 内部処理：ファイル保存 → DB insert → 生成ID返却 */
    private Long doSave(Map<String, Object> data, MultipartFile file, String userId) throws Exception {
        // ディレクトリ yyyy/MM を作成
        LocalDate baseDate = parseDate((String) data.get("date"));
        if (baseDate == null) baseDate = LocalDate.now();
        String y = String.valueOf(baseDate.getYear());
        String m = String.format("%02d", baseDate.getMonthValue());

        File dir = new File(uploadRoot, y + File.separator + m);
        if (!dir.exists()) Files.createDirectories(dir.toPath());

        // 物理保存
        String filename = System.currentTimeMillis() + "_" +
                Optional.ofNullable(file.getOriginalFilename()).orElse("receipt.jpg");
        File dest = new File(dir, filename);
        file.transferTo(dest);

        // 公開URL生成
        String relativeUrl = (publicPrefix.endsWith("/") ? publicPrefix : publicPrefix + "/")
                + y + "/" + m + "/" + filename;

        // エンティティ詰替
        ReceiptInfo info = new ReceiptInfo();
        info.setImagePath(relativeUrl);
        info.setIssuer((String) data.get("issuer"));
        info.setIssueDate(parseDate((String) data.get("date")));
        info.setAmount(parseAmountToLong((String) data.get("amount")));
        info.setFullText((String) data.get("full_text"));
        info.setUserId(Long.parseLong(userId));
        info.setStatus("確認済");
        info.setCreatedAt(LocalDateTime.now());

        // DB insert
        receiptInfoMapper.insert(info);
        return info.getId();
    }

    // ------------------------------------------------------------
    // 3) 更新（項目のみ）
    // ------------------------------------------------------------
    @Override
    public int updateOne(Long id, String issuer, String number, String date, String amount, String userId) throws Exception {
        ReceiptInfo info = new ReceiptInfo();
        info.setId(id);
        info.setIssuer(issuer);
        info.setIssueDate(parseDate(date));
        info.setAmount(parseAmountToLong(amount));
        info.setUserId(Long.valueOf(userId));
        return receiptInfoMapper.updateMeta(info);
    }

    // ------------------------------------------------------------
    // 4) 削除（保存取消）
    // ------------------------------------------------------------
    @Override
    public int deleteOne(Long id, String userId) throws Exception {
        return receiptInfoMapper.deleteByIdAndUser(id, Long.valueOf(userId));
    }

    // ------------------------------------------------------------
    // 5) Excel 出力
    // ------------------------------------------------------------
    @Override
    public byte[] exportExcel(String userId) throws Exception {
        List<ReceiptInfoListItem> list = receiptInfoMapper.findByUserId(Long.valueOf(userId));

        try (XSSFWorkbook wb = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            var sh = wb.createSheet("receipts");
            int r = 0;

            // ヘッダー
            String[] heads = {"ID", "発行先", "登録番号", "発行日", "金額", "ステータス", "画像パス", "登録日時"};
            Row header = sh.createRow(r++);
            for (int c = 0; c < heads.length; c++) header.createCell(c).setCellValue(heads[c]);

            // データ行
            for (ReceiptInfoListItem it : list) {
                Row row = sh.createRow(r++);
                int c = 0;
                row.createCell(c++).setCellValue(it.getId() == null ? 0 : it.getId());
                row.createCell(c++).setCellValue(nz(it.getIssuer()));
                try {
                    var m = it.getClass().getMethod("getRegistrationNumber");
                    Object v = m.invoke(it);
                    row.createCell(c++).setCellValue(v == null ? "" : v.toString());
                } catch (NoSuchMethodException ignore) {
                    row.createCell(c++).setCellValue("");
                }
                row.createCell(c++).setCellValue(it.getIssueDate() == null ? "" : it.getIssueDate().toString());
                row.createCell(c++).setCellValue(it.getAmount() == null ? 0 : it.getAmount());
                row.createCell(c++).setCellValue(nz(it.getStatus()));
                row.createCell(c++).setCellValue(nz(it.getImagePath()));
                row.createCell(c++).setCellValue(it.getCreatedAt() == null ? "" : it.getCreatedAt().toString());
            }
            for (int i = 0; i < heads.length; i++) sh.autoSizeColumn(i);

            wb.write(out);
            return out.toByteArray();
        }
    }

    // ------------------------------------------------------------
    // ユーティリティ
    // ------------------------------------------------------------
    /** 発行先推定 */
    private String extractIssuer(String text) {
        String[] lines = text.split("\\R");
        for (String line : lines) {
            if (line.matches(".*(株式会社|有限会社|商店|店|センター|クリニック|病院|大学|ホテル|ENEOS|ローソン|ファミリーマート).*")) {
                return line.trim();
            }
        }
        return lines.length > 0 ? lines[0].trim() : "";
    }

    /** 登録番号抽出 */
    private String extractRegistrationNumber(String text) {
        Matcher m1 = Pattern.compile("T\\s*\\d{13}").matcher(text);
        if (m1.find()) return m1.group().replaceAll("\\s+", "");
        Matcher m2 = Pattern.compile("(登録番号|登録番)[:：]?\\s*T?\\s*\\d{13}").matcher(text);
        if (m2.find()) return m2.group().replaceAll("[^T\\d]", "");
        return null;
    }

    /** 日付抽出・正規化 */
    private String extractDateString(String text) {
        String[] patterns = {"\\d{4}年\\d{1,2}月\\d{1,2}日","\\d{4}/\\d{1,2}/\\d{1,2}","\\d{4}-\\d{1,2}-\\d{1,2}","\\d{1,2}/\\d{1,2}"};
        for (String p : patterns) {
            Matcher m = Pattern.compile(p).matcher(text);
            if (m.find()) {
                LocalDate d = parseDate(m.group());
                return d == null ? null : d.toString();
            }
        }
        return null;
    }

    /** 金額抽出 */
    private String extractAmountString(String text) {
        Pattern pattern = Pattern.compile("(合計|合算|税込|金額)[:：]?\\s*[¥￥]?\\s*\\d{1,3}(,\\d{3})*(\\.\\d{1,2})?");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            Matcher amountOnly = Pattern.compile("\\d{1,3}(,\\d{3})*(\\.\\d{1,2})?").matcher(matcher.group());
            if (amountOnly.find()) return amountOnly.group();
        }
        Matcher fallback = Pattern.compile("[¥￥]?\\s*\\d{1,3}(,\\d{3})*(\\.\\d{1,2})?").matcher(text);
        return fallback.find() ? fallback.group().replaceAll("^[¥￥]\\s*", "") : null;
    }

    /** 文字列→LocalDate */
    private LocalDate parseDate(String raw) {
        if (raw == null || raw.isBlank()) return null;
        String[] fmts = {"yyyy年M月d日","yyyy/M/d","yyyy-M-d","M/d"};
        for (String f : fmts) {
            try {
                LocalDate d = LocalDate.parse(raw, DateTimeFormatter.ofPattern(f));
                if ("M/d".equals(f)) d = d.withYear(LocalDate.now().getYear());
                return d;
            } catch (Exception ignore) {}
        }
        return null;
    }

    /** 金額文字列→Long */
    private Long parseAmountToLong(String amountText) {
        if (amountText == null) return null;
        String clean = amountText.replaceAll("[^\\d]", "");
        if (clean.isEmpty()) return null;
        return Long.valueOf(clean);
    }

    private static String nz(String s) { return s == null ? "" : s; }

    private static String safeGet(List<String> list, int i) {
        return (list != null && list.size() > i && list.get(i) != null) ? list.get(i) : "";
    }
}
