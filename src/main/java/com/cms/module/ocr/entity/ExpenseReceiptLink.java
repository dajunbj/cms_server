package com.cms.module.ocr.entity;

public class ExpenseReceiptLink {
	private Long id;
	private Long expenseRequestId;
	private Long receiptId;

	// getters/setters...
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