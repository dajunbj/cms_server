package com.cms.module.ocr.entity;

import java.sql.Timestamp;

/**
 * OCR読取結果を保存する領収書エンティティクラス。
 */
public class ReceiptRecord {
    private Long id;                 // 自動採番ID
    private String issuer;           // 発行元
    private String date;             // 発行日
    private String amount;           // 金額
    private String fullText;         // OCR全文
    private String userId;           // ユーザーID
    private String receiptKey;       // 重複防止用の一意キー（OCR全文などのハッシュ）
    private Timestamp createdAt;     // 登録日時（DB自動設定）

    // --- Getters and Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getIssuer() { return issuer; }
    public void setIssuer(String issuer) { this.issuer = issuer; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getAmount() { return amount; }
    public void setAmount(String amount) { this.amount = amount; }

    public String getFullText() { return fullText; }
    public void setFullText(String fullText) { this.fullText = fullText; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getReceiptKey() { return receiptKey; }
    public void setReceiptKey(String receiptKey) { this.receiptKey = receiptKey; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}