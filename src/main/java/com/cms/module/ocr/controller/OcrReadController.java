package com.cms.module.ocr.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cms.module.ocr.service.OcrReadService;

import lombok.RequiredArgsConstructor;

/**
 * ================================================================
 * OCR 読み取り／保存／エクスポート用コントローラ
 *
 * フロント要件に対応する API 一覧:
 *   1) 選択分OCR           → POST   /api/ocr                  (file)
 *   2) 一括OCR             → POST   /api/ocr/parse-batch      (files[])
 *   3) 現在を保存          → POST   /api/ocr/save-with-image  (file + form)
 *   4) 一括保存            → POST   /api/ocr/save-all         (files[] + form[])
 *   5) Excel 出力          → GET    /api/ocr/export.xlsx
 *   6) 更新保存（項目のみ） → POST   /api/ocr/update
 *   7) 保存取消（削除）     → DELETE /api/ocr/{id}
 *
 *
 * 返却はフロント側でそのまま行ステータス更新できるよう、
 * status を「読取済み／保存済み／保存失敗」など日本語で返す。
 * ================================================================
 */
@RestController
@RequestMapping("/api/ocr")
@RequiredArgsConstructor
public class OcrReadController {

    private final OcrReadService ocrReadService;

    // ------------------------------------------------------------
    // 1) 選択分OCR：単一ファイル
    // ------------------------------------------------------------
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> parseOne(
            @RequestParam("file") MultipartFile file) throws Exception {

        Map<String, Object> data = ocrReadService.parse(file); // issuer/number/date/amount/full_text
        Map<String, Object> body = new HashMap<>(data);
        body.put("status", "読取済み");
        return ResponseEntity.ok(body);
    }

    // ------------------------------------------------------------
    // 2) 一括OCR：複数ファイル（未読のみをフロントで送付）
    // ------------------------------------------------------------
    @PostMapping(value = "/parse-batch", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> parseBatch(
            @RequestPart("files") List<MultipartFile> files) {

        List<Map<String, Object>> results = new ArrayList<>();
        int success = 0;

        for (int i = 0; i < files.size(); i++) {
            MultipartFile f = files.get(i);
            try {
                Map<String, Object> data = ocrReadService.parse(f);
                Map<String, Object> row = new HashMap<>(data);
                row.put("index", i);
                row.put("status", "読取済み");
                results.add(row);
                success++;
            } catch (Exception e) {
                Map<String, Object> row = new HashMap<>();
                row.put("index", i);
                row.put("error", e.getMessage());
                row.put("status", "読み取り失敗");
                results.add(row);
            }
        }

        Map<String, Object> body = Map.of(
                "total", files.size(),
                "success", success,
                "items", results
        );
        return ResponseEntity.ok(body);
    }

    // ------------------------------------------------------------
    // 3) 現在を保存：単一件（画像＋フォーム値）
    // ------------------------------------------------------------
    @PostMapping(value = "/save-with-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> saveWithImage(
            @RequestParam("issuer") String issuer,
            @RequestParam(value = "number", required = false) String number,
            @RequestParam("date") String date,
            @RequestParam("amount") String amount,
            @RequestParam(value = "full_text", required = false) String fullText,
            @RequestParam("file") MultipartFile file,
            @RequestHeader(value = "X-User-Id", required = false) String userId
    ) throws Exception {
        if (userId == null || userId.isBlank()) userId = "1";
        Long id = ocrReadService.saveOne(issuer, number, date, amount, fullText, file, userId);

        return ResponseEntity.ok(Map.of(
                "id", id,
                "status", "保存済み",
                "message", "保存成功"
        ));
    }

    // ------------------------------------------------------------
    // 4) 一括保存：複数件（files[] + form[]）
    // ------------------------------------------------------------
    @PostMapping(value = "/save-all", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> saveAll(
            @RequestPart("files") List<MultipartFile> files,
            @RequestParam("issuer") List<String> issuer,
            @RequestParam(value = "number", required = false) List<String> number,
            @RequestParam("date") List<String> date,
            @RequestParam("amount") List<String> amount,
            @RequestParam(value = "full_text", required = false) List<String> fullText,
            @RequestHeader(value = "X-User-Id", required = false) String userId
    ) throws Exception {
        if (userId == null || userId.isBlank()) userId = "1";

        List<Long> ids = ocrReadService.saveBatch(files, issuer, number, date, amount, fullText, userId);

        int total = files.size();
        int success = (int) ids.stream().filter(java.util.Objects::nonNull).count();

        List<Map<String, Object>> items = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            Map<String, Object> row = new HashMap<>();
            row.put("index", i);
            if (ids.get(i) != null) {
                row.put("id", ids.get(i));
                row.put("status", "保存済み");
            } else {
                row.put("status", "保存失敗");
            }
            items.add(row);
        }

        Map<String, Object> body = Map.of(
                "total", total,
                "success", success,
                "ids", ids,
                "items", items,
                "message", "一括保存完了"
        );
        return ResponseEntity.ok(body);
    }

    // ------------------------------------------------------------
    // 5) Excel 出力
    // ------------------------------------------------------------
    @GetMapping(value = "/export.xlsx")
    public ResponseEntity<byte[]> exportExcel(
            @RequestHeader(value = "X-User-Id", required = false) String userId
    ) throws Exception {
        if (userId == null || userId.isBlank()) userId = "1";

        byte[] bytes = ocrReadService.exportExcel(userId);
        String filename = URLEncoder.encode("receipts.xlsx", StandardCharsets.UTF_8);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + filename)
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(bytes);
    }

    // ------------------------------------------------------------
    // 6) 更新保存（画像なし・項目のみ）
    // ------------------------------------------------------------
    @PostMapping("/update")
    public ResponseEntity<Map<String, Object>> updateMeta(
            @RequestParam("id") Long id,
            @RequestParam("issuer") String issuer,
            @RequestParam(value = "number", required = false) String number,
            @RequestParam("date") String date,
            @RequestParam("amount") String amount,
            @RequestHeader(value = "X-User-Id", required = false) String userId
    ) {
        try {
            if (userId == null || userId.isBlank()) userId = "1";
            ocrReadService.updateOne(id, issuer, number, date, amount, userId);
            return ResponseEntity.ok(Map.of("message", "更新成功", "status", "保存済み"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    // ------------------------------------------------------------
    // 7) 保存取消（削除）
    // ------------------------------------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteOne(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Id", required = false) String userId
    ) {
        try {
            if (userId == null || userId.isBlank()) userId = "1";
            int n = ocrReadService.deleteOne(id, userId);
            if (n == 0) {
                return ResponseEntity.status(404).body(Map.of("error", "not found"));
            }
            return ResponseEntity.ok(Map.of("deleted", n, "status", "取消済み"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
}
