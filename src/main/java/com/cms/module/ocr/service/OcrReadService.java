package com.cms.module.ocr.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

/**
 * OCR読取処理サービス
 *
 * 機能概要:
 * - Google Vision API を利用したOCR解析
 * - OCR結果から発行元・発行日・金額を抽出
 * - 領収書画像とOCR結果をローカル保存 + DB登録
 */
@Service
public class OcrReadService {

    @Autowired
    private ReceiptInfoMapper receiptInfoMapper;

    /**
     * OCR解析（Google Vision API）
     *
     * 手順:
     * 1. 環境変数 GOOGLE_OCR_KEY_PATH から認証キーを読み込む
     * 2. Google Vision API へ画像を送信
     * 3. OCR結果から発行元・発行日・金額を抽出
     *
     * @param file アップロードされた領収書画像
     * @return OCR結果（発行元、日付、金額、全文）
     * @throws Exception API呼び出し失敗など
     */
    public Map<String, Object> parse(MultipartFile file) throws Exception {
        String keyPath = System.getenv("GOOGLE_OCR_KEY_PATH");
        if (keyPath == null || keyPath.isEmpty()) {
            throw new IllegalStateException("Environment variable GOOGLE_OCR_KEY_PATH not set.");
        }

        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(keyPath));
        ImageAnnotatorSettings settings = ImageAnnotatorSettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build();

        ByteString imgBytes = ByteString.readFrom(file.getInputStream());
        Image img = Image.newBuilder().setContent(imgBytes).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.DOCUMENT_TEXT_DETECTION).build();
        ImageContext context = ImageContext.newBuilder().addLanguageHints("ja").build();

        AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                .addFeatures(feat).setImage(img).setImageContext(context).build();

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create(settings)) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(List.of(request));
            AnnotateImageResponse res = response.getResponses(0);

            if (res.hasError()) {
                throw new RuntimeException("OCR Error: " + res.getError().getMessage());
            }

            // OCR全文
            String fullText = res.getFullTextAnnotation().getText();

            // 結果マップを作成
            Map<String, Object> result = new HashMap<>();
            result.put("issuer", extractIssuer(fullText));
            result.put("date", extractDate(fullText));
            result.put("amount", extractAmount(fullText));
            result.put("full_text", fullText);

            return result;
        }
    }

    /**
     * OCR結果と画像をDBに保存（receipt_info テーブル）
     *
     * 保存内容:
     * - 発行元（issuer）
     * - 発行日（date）
     * - 金額（amount）
     * - OCR全文（full_text）
     * - 画像相対パス（/uploads/年/月/ファイル名）
     * - ユーザーID
     * - ステータス（初期値: 確認済）
     *
     * @param data   OCR結果
     * @param file   領収書画像
     * @param userId ユーザーID
     * @throws IOException 画像保存失敗など
     */
    public void saveToDatabaseWithImage(
            Map<String, Object> data, MultipartFile file, String userId)
            throws IOException {

        ReceiptInfo info = new ReceiptInfo();

        // 日付別ディレクトリ作成 (年/月)
        LocalDate baseDate = parseDate((String) data.get("date"));
        if (baseDate == null) baseDate = LocalDate.now();
        String y = String.valueOf(baseDate.getYear());
        String m = String.format("%02d", baseDate.getMonthValue());

        // 物理保存先: C:\Users\...\ocr_image_save\年\月\
        File dir = new File("C:\\Users\\Micheal\\Desktop\\ocr_image_save", y + File.separator + m);
        if (!dir.exists()) dir.mkdirs();

        // ファイル名: タイムスタンプ + 元ファイル名
        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File dest = new File(dir, filename);
        file.transferTo(dest);

        // 相対パス（DB保存用）
        String relativeUrl = "/uploads/" + y + "/" + m + "/" + filename;
        info.setImagePath(relativeUrl);

        // OCRフィールド設定
        info.setStoreName((String) data.get("issuer"));
        info.setIssueDate(parseDate((String) data.get("date")));
        info.setAmount(parseAmount((String) data.get("amount")));
        info.setFullText((String) data.get("full_text"));
        info.setUserId(Long.parseLong(userId));
        info.setStatus("確認済");
        info.setCreatedAt(java.time.LocalDateTime.now());

        // デバッグ出力
        System.out.println("storeName = " + data.get("issuer"));
        System.out.println("date      = " + data.get("date"));
        System.out.println("amount    = " + data.get("amount"));
        System.out.println("fullText  = " + data.get("full_text"));

        // DB保存
        receiptInfoMapper.insert(info);
    }

    // ================== OCR 抽出処理 ==================

    /** 発行元抽出 */
    private String extractIssuer(String text) {
        String[] lines = text.split("\n");
        for (String line : lines) {
            if (line.matches(".*(株式会社|有限会社|商店|店|センター|クリニック).*")) {
                return line.trim();
            }
        }
        return lines.length > 0 ? lines[0].trim() : "";
    }

    /** 日付抽出 */
    private String extractDate(String text) {
        String[] patterns = {
                "\\d{4}年\\d{1,2}月\\d{1,2}日",
                "\\d{4}/\\d{1,2}/\\d{1,2}",
                "\\d{4}-\\d{1,2}-\\d{1,2}",
                "\\d{1,2}/\\d{1,2}"
        };
        for (String pattern : patterns) {
            Matcher m = Pattern.compile(pattern).matcher(text);
            if (m.find()) return m.group();
        }
        return null;
    }

    /** 文字列→LocalDate */
    private LocalDate parseDate(String raw) {
        if (raw == null) return null;
        String[] patterns = { "yyyy年M月d日", "yyyy/M/d", "yyyy-M-d", "M/d" };
        for (String pattern : patterns) {
            try {
                return LocalDate.parse(raw, DateTimeFormatter.ofPattern(pattern));
            } catch (Exception e) {
                // continue
            }
        }
        return null;
    }

    /** 金額抽出 */
    private String extractAmount(String text) {
        Pattern pattern = Pattern.compile("(合計|合算|税込|金額)[:：]?\\s*[¥￥]?\\s*\\d{1,3}(,\\d{3})*(\\.\\d{1,2})?");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            String match = matcher.group();
            Matcher amountOnly = Pattern.compile("\\d{1,3}(,\\d{3})*(\\.\\d{1,2})?").matcher(match);
            return amountOnly.find() ? amountOnly.group() : null;
        }

        // fallback: 単純な金額っぽい数字
        Matcher fallback = Pattern.compile("[¥￥]?\\s*\\d{1,3}(,\\d{3})*(\\.\\d{1,2})?").matcher(text);
        return fallback.find() ? fallback.group().trim() : null;
    }

    /** 金額文字列 → Long */
    private Long parseAmount(String amountText) {
        if (amountText == null) return null;
        String clean = amountText.replaceAll("[^\\d]", ""); // 数字以外除去
        if (clean.isEmpty()) return null;
        return Long.valueOf(clean);
    }
}
