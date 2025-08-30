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

/**
 * 領収書一覧取得専用コントローラ。
 * - OCR処理(OcrReadController)とは役割を分離し、
 *   一覧・検索・集計専用のエンドポイントを提供。
 */
@RestController
@RequestMapping("/api/ocr/receipts") // ← URLプレフィックスを /api/ocr/receipts に固定
public class ReceiptQueryController {

    @Autowired
    private ReceiptQueryService receiptQueryService;

    /**
     * 領収書の一覧を取得（検索条件 + ページネーション対応）
     *
     * @param page      ページ番号（1開始）。デフォルト: 1
     * @param size      1ページの件数。デフォルト: 10
     * @param Issuer 店名検索条件（部分一致対応想定）
     * @param status    ステータス検索条件（例: 未申請, 申請中, 承認済）
     * @return JSON { items: [...], total: 件数, page: ページ番号, size: 件数 }
     */
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getReceiptList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String Issuer,
            @RequestParam(required = false) String status
    ) {
        try {
            // 基本的な境界処理
            if (page < 1) page = 1;
            if (size < 1) size = 10;

            // DTO に詰め替え
            ReceiptQueryCondition cond = new ReceiptQueryCondition();
            cond.setOffset((page - 1) * size);
            cond.setLimit(size);
            cond.setIssuer(Issuer);
            cond.setStatus(status);

            // データ取得
            List<ReceiptInfoListItem> list = receiptQueryService.queryListByCondition(cond);
            int total = receiptQueryService.countByCondition(cond);

            // JSON形式で返却
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
