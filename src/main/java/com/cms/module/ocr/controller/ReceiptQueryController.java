package com.cms.module.ocr.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cms.module.ocr.dto.ReceiptInfoListItem;
import com.cms.module.ocr.dto.ReceiptQueryCondition;
import com.cms.module.ocr.service.ReceiptQueryService;

@RestController
@RequestMapping("/api/ocr/receipts") // ← 与 OcrReadController 区分开
public class ReceiptQueryController {

    @Autowired
    private ReceiptQueryService receiptQueryService;

    /** 一覧（条件＋ページネーション） */
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getReceiptList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String storeName,
            @RequestParam(required = false) String status
    ) {
        try {
            // 基本边界处理
            if (page < 1) page = 1;
            if (size < 1) size = 10;

            ReceiptQueryCondition cond = new ReceiptQueryCondition();
            cond.setOffset((page - 1) * size);
            cond.setLimit(size);
            cond.setStoreName(storeName);
            cond.setStatus(status);

            List<ReceiptInfoListItem> list = receiptQueryService.queryListByCondition(cond);
            int total = receiptQueryService.countByCondition(cond);

            return ResponseEntity.ok(Map.of(
                "items", list,
                "total", total,
                "page", page,
                "size", size
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
}
