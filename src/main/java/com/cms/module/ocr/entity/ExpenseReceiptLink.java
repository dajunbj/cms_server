package com.cms.module.ocr.entity;

/**
 * 経費申請と領収書の関連テーブルに対応するエンティティ。
 *
 * 用途:
 * - 1件の経費申請 (ExpenseRequest) に複数の領収書 (ReceiptInfo) を紐付けるための中間テーブル。
 * - 多対多関係を表現するためのリンクエンティティ。
 *
 * 例:
 *   expense_request.id = 1001
 *   └── expense_receipt_link
 *          id=1, expense_request_id=1001, receipt_id=2001
 *          id=2, expense_request_id=1001, receipt_id=2002
 *   → この場合、申請1001に領収書2001と2002が紐付いている。
 */
public class ExpenseReceiptLink {

    /** 主キーID */
    private Long id;

    /** 経費申請ID (FK: expense_request.id) */
    private Long expenseRequestId;

    /** 領収書ID (FK: receipt_info.id) */
    private Long receiptId;

    // ================== Getter / Setter ==================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExpenseRequestId() {
        return expenseRequestId;
    }

    public void setExpenseRequestId(Long expenseRequestId) {
        this.expenseRequestId = expenseRequestId;
    }

    public Long getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Long receiptId) {
        this.receiptId = receiptId;
    }
}
