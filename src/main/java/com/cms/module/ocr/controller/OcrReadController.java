package com.cms.module.ocr.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cms.module.ocr.service.OcrReadService;

/**
 * OCR 読み取り・保存用コントローラ
 * 
 * 機能概要:
 * - 領収書画像をアップロードしてOCRを実行し、結果を返却
 * - 領収書の画像およびOCR結果をデータベースに保存
 */
@RestController
@RequestMapping("/api/ocr")
public class OcrReadController {

    @Autowired
    private OcrReadService ocrReadService;

    /**
     * OCR読み取り（ファイルアップロード → OCR実行 → 結果返却）
     *
     * @param file 領収書画像ファイル
     * @return OCR結果（発行元、日付、金額、全文 など）を含むMap
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> parseReceipt(@RequestParam("file") MultipartFile file) {
        try {
            return ResponseEntity.ok(ocrReadService.parse(file));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * OCR結果と画像を一緒に保存
     * 
     * 保存内容:
     * - 発行元（issuer）
     * - 発行日（date）
     * - 金額（amount）
     * - OCR全文（fullText）
     * - 領収書画像（file）
     * - ユーザー情報（userId: ヘッダーから取得、なければ "1"）
     *
     * @param issuer    発行元（店舗名など）
     * @param date      発行日
     * @param amount    金額
     * @param fullText  OCR全文
     * @param file      領収書画像ファイル
     * @param userId    ユーザーID（X-User-Id ヘッダーから取得）
     * @return 保存結果メッセージ
     */
    @PostMapping("/save-with-image")
    public ResponseEntity<String> saveWithImage(
            @RequestParam("issuer") String issuer,
            @RequestParam("date") String date,
            @RequestParam("amount") String amount,
            @RequestParam("full_text") String fullText,
            @RequestParam("file") MultipartFile file,
            @RequestHeader(value = "X-User-Id", required = false) String userId) {
        try {
            if (userId == null || userId.isBlank()) userId = "1"; // TODO: 認証導入後は適切に取得する
            ocrReadService.saveToDatabaseWithImage(Map.of(
                    "issuer", issuer,
                    "date", date,
                    "amount", amount,
                    "fullText", fullText
            ), file, userId);
            return ResponseEntity.ok("保存成功（画像付き）");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("保存失敗: " + e.getMessage());
        }
    }
}
