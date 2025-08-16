package com.cms.module.ocr.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.module.ocr.service.ExpenseRequestService;

/**
 * 経費申請 作成用コントローラ
 * - 一括選択した領収書IDの配列から経費申請を1件作成し、リンクテーブルに関連付ける。
 * - 申請後、該当の領収書ステータスを「申請済」に更新する。
 */
@RestController
@RequestMapping("/api/expense/requests")
public class ExpenseRequestController {

    @Autowired
    private ExpenseRequestService expenseRequestService;

    /**
     * 経費申請の新規作成（領収書の一括申請）
     *
     * Request (JSON):
     * {
     *   "receiptIds": [1,2,3],  // 必須：選択した領収書ID
     *   "summary": "出張精算 8月"  // 任意：摘要
     * }
     *
     * Header:
     *   X-User-Id: 申請者ユーザーID（ログイン連携までは暫定で任意）
     *
     * Response (200):
     * { "id": <新規作成された expense_request.id> }
     *
     * エラー:
     *  - 400: バリデーションやドメインエラー（申請不可の領収書が含まれる等）
     *  - 500: サーバ内部エラー
     */
    @PostMapping
    public ResponseEntity<?> create(
            @RequestBody Map<String, Object> body,
            @RequestHeader(value = "X-User-Id", required = false) String userId
    ) {
        try {
            // 一時的にログイン連携がない前提のフォールバック
            if (userId == null || userId.isBlank()) userId = "1";

            @SuppressWarnings("unchecked")
            List<Integer> idsInt = (List<Integer>) body.get("receiptIds");
            if (idsInt == null || idsInt.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "receiptIds is empty"));
            }
            String summary = (String) body.getOrDefault("summary", "");

            // JSON 数値は Integer になりやすいので Long に変換
            List<Long> receiptIds = idsInt.stream()
                    .map(Integer::longValue)
                    .toList();

            Long reqId = expenseRequestService.createRequest(Long.valueOf(userId), summary, receiptIds);
            return ResponseEntity.ok(Map.of("id", reqId));
        } catch (IllegalArgumentException | IllegalStateException e) {
            // 入力不備や申請不可などドメインエラーは 400
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            // 予期しないエラーは 500
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
}
