package com.cms.module.ocr.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping
    public ResponseEntity<Map<String, Object>> parseReceipt(@RequestParam("file") MultipartFile file) {
        try {
            return ResponseEntity.ok(ocrReadService.parse(file));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
    @PostMapping("/save")
    public ResponseEntity<String> saveOcrResult(@RequestBody Map<String, Object> data,
                                                @RequestHeader(value = "X-User-Id", required = false) String userId) {
        try {
            // 如果前端未传 userId，可以从 session 或 token 中解析（此处为示例）
            if (userId == null) {
                userId = "anonymous"; // 或抛异常、从SecurityContext中获取等
            }

            ocrReadService.saveToDatabase(data, userId);
            return ResponseEntity.ok("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("保存失敗: " + e.getMessage());
        }
    }


}