package com.cms.module.ocr.service;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cms.module.ocr.entity.ReceiptRecord;
import com.cms.module.ocr.mapper.ReceiptRecordMapper;
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

@Service
public class OcrReadService {

    public Map<String, Object> parse(MultipartFile file) throws Exception {
        String keyPath = System.getenv("GOOGLE_OCR_KEY_PATH");
        if (keyPath == null || keyPath.isEmpty()) {
            throw new IllegalStateException("Environment variable GOOGLE_OCR_KEY_PATH not set.");
        }

        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(keyPath));
        ImageAnnotatorSettings settings = ImageAnnotatorSettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                .build();

        ByteString imgBytes = ByteString.readFrom(file.getInputStream());
        Image img = Image.newBuilder().setContent(imgBytes).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.DOCUMENT_TEXT_DETECTION).build();
        ImageContext context = ImageContext.newBuilder().addLanguageHints("ja").build();

        AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                .addFeatures(feat)
                .setImage(img)
                .setImageContext(context)
                .build();

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create(settings)) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(List.of(request));
            AnnotateImageResponse res = response.getResponses(0);

            if (res.hasError()) {
                throw new RuntimeException("OCR Error: " + res.getError().getMessage());
            }

            String fullText = res.getFullTextAnnotation().getText();
            Map<String, Object> result = new HashMap<>();
            result.put("OCR全文", fullText);
            result.put("発行元", extractIssuer(fullText));
            result.put("発行日", extractDate(fullText));
            result.put("金額", extractAmount(fullText));

            return result;
        }
    }
    
    @Autowired
    private ReceiptRecordMapper receiptRecordMapper;

    /**
     * OCR解析結果をDBに保存。重複登録を防止する。
     * @param data フロントから送られるOCR項目マップ
     * @param userId 現在のユーザーID（ログイン情報から取得想定）
     */
    public void saveToDatabase(Map<String, Object> data, String userId) {
        ReceiptRecord record = new ReceiptRecord();
        record.setIssuer((String) data.get("発行元"));
        record.setDate((String) data.get("発行日"));
        record.setAmount((String) data.get("金額"));
        record.setFullText((String) data.get("OCR全文"));
        record.setUserId(userId);

        // OCR全文を元に一意キーを生成（MD5ハッシュ）
        String hash = org.springframework.util.DigestUtils.md5DigestAsHex(
            record.getFullText().getBytes(java.nio.charset.StandardCharsets.UTF_8)
        );
        record.setReceiptKey(hash);

        // 重複チェック
        if (receiptRecordMapper.existsByReceiptKey(hash)) {
            throw new IllegalStateException("同じ領収書はすでに保存されています");
        }

        // 保存
        receiptRecordMapper.insert(record);
    }





    private String extractIssuer(String text) {
        String[] lines = text.split("\n");
        for (String line : lines) {
            if (line.matches(".*(株式会社|有限会社|商店|店|センター|クリニック).*")) {
                return line.trim();
            }
        }
        return lines.length > 0 ? lines[0].trim() : "";
    }

    private String extractDate(String text) {
        String[] patterns = {
            "\\d{4}年\\d{1,2}月\\d{1,2}日",      // 2024年6月7日
            "\\d{4}/\\d{1,2}/\\d{1,2}",          // 2024/06/07
            "\\d{4}-\\d{1,2}-\\d{1,2}",          // 2024-06-07
            "\\d{1,2}/\\d{1,2}"                   // 06/07（年不含）
        };
        for (String pattern : patterns) {
            Matcher m = Pattern.compile(pattern).matcher(text);
            if (m.find()) return m.group();
        }
        return null;
    }

    private String extractAmount(String text) {
        Pattern pattern = Pattern.compile("(合計|合算|税込|金額)[:：]?\\s*[¥￥]?\\s*\\d{1,3}(,\\d{3})*(\\.\\d{1,2})?");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            String match = matcher.group();
            Matcher amountOnly = Pattern.compile("\\d{1,3}(,\\d{3})*(\\.\\d{1,2})?").matcher(match);
            return amountOnly.find() ? amountOnly.group() : null;
        }

        Matcher fallback = Pattern.compile("[¥￥]?\\s*\\d{1,3}(,\\d{3})*(\\.\\d{1,2})?").matcher(text);
        return fallback.find() ? fallback.group().trim() : null;
    }
}
