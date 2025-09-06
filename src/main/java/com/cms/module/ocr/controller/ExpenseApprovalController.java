package com.cms.module.ocr.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cms.module.ocr.dto.ReceiptInfoListItem;
import com.cms.module.ocr.entity.ExpenseRequest;
import com.cms.module.ocr.service.ExpenseApprovalService;

/**
 * 経費承認処理コントローラ
 * - 申請一覧の取得
 * - 申請詳細の確認（領収書一覧付き）
 * - 承認／差戻し操作
 */
@RestController
@RequestMapping("/api/expense/approval")
public class ExpenseApprovalController {

    @Autowired
    private ExpenseApprovalService service;

    /**
     * 承認待ち申請の一覧取得
     *
     * GET /api/expense/approval/list?page=1&size=10&status=申請済&applicantId=123
     *
     * @param page ページ番号（1始まり、デフォルト1）
     * @param size 1ページあたり件数（デフォルト10）
     * @param status 状態フィルタ（例: "申請済", "承認済", null=全件）
     * @param applicantId 申請者IDによる絞込（null=全件）
     * @return items:申請リスト, total:総件数, page/size:ページ情報
     */
    @GetMapping("/list")
    public ResponseEntity<?> list(@RequestParam(defaultValue = "1") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(required = false) String status,
                                  @RequestParam(required = false) Long applicantId) {
        try {
            List<ExpenseRequest> items = service.list(status, page, size, applicantId);
            int total = service.count(status, applicantId);
            return ResponseEntity.ok(Map.of(
                "items", items,
                "total", total,
                "page", page,
                "size", size
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * 申請詳細の取得（領収書リスト付き）
     *
     * GET /api/expense/approval/{id}
     *
     * @param id 経費申請ID
     * @return request:申請情報, receipts:領収書一覧
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id) {
        ExpenseRequest req = service.findById(id);
        if (req == null) {
            return ResponseEntity.status(404).body(Map.of("error", "not found"));
        }
        List<ReceiptInfoListItem> receipts = service.findReceipts(id);
        return ResponseEntity.ok(Map.of("request", req, "receipts", receipts));
    }

    /**
     * 承認操作
     *
     * POST /api/expense/approval/{id}/approve
     * Header: X-User-Id = 承認者ユーザーID
     *
     * @param id 経費申請ID
     * @return ok=true (成功), 400=状態不正, 500=サーバエラー
     */
    @PostMapping("/{id}/approve")
    public ResponseEntity<?> approve(@PathVariable Long id,
                                     @RequestHeader(value="X-User-Id", required=false) String userId) {
        if (userId == null || userId.isBlank()) userId = "1"; // 暫定: ログイン未連携
        boolean ok = service.approve(id, Long.valueOf(userId));
        if (!ok) {
            return ResponseEntity.badRequest().body(Map.of("error", "状態が不正です（申請済のみ承認可）"));
        }
        return ResponseEntity.ok(Map.of("ok", true));
    }

    /**
     * 差戻し操作
     *
     * POST /api/expense/approval/{id}/reject
     * Body: { "reason": "領収書不備あり" }
     * Header: X-User-Id = 承認者ユーザーID
     *
     * @param id 経費申請ID
     * @param reason 差戻し理由（任意）
     * @return ok=true (成功), 400=状態不正, 500=サーバエラー
     */
    @PostMapping("/{id}/reject")
    public ResponseEntity<?> reject(@PathVariable Long id,
                                    @RequestBody Map<String, String> body,
                                    @RequestHeader(value="X-User-Id", required=false) String userId) {
        if (userId == null || userId.isBlank()) userId = "1";
        String reason = body.getOrDefault("reason", "");
        boolean ok = service.reject(id, Long.valueOf(userId), reason);
        if (!ok) {
            return ResponseEntity.badRequest().body(Map.of("error", "状態が不正です（申請済のみ差し戻し可）"));
        }
        return ResponseEntity.ok(Map.of("ok", true));
    }
}
