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

@RestController
@RequestMapping("/api/ocr")
public class OcrReadController {

    @Autowired
    private OcrReadService ocrReadService;

    /** OCR読み取り（ファイルアップロード → OCR実行 → 結果返却） */
    @PostMapping
    public ResponseEntity<Map<String, Object>> parseReceipt(@RequestParam("file") MultipartFile file) {
        try {
            return ResponseEntity.ok(ocrReadService.parse(file));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    /** 画像とOCR項目を一緒に保存（推奨方式） */
    @PostMapping("/save-with-image")
    public ResponseEntity<String> saveWithImage(
            @RequestParam("issuer") String issuer,
            @RequestParam("date") String date,
            @RequestParam("amount") String amount,
            @RequestParam("full_text") String fullText,
            @RequestParam("file") MultipartFile file,
            @RequestHeader(value = "X-User-Id", required = false) String userId) {
        try {
            if (userId == null || userId.isBlank()) userId = "1"; // 临时：数字型
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
