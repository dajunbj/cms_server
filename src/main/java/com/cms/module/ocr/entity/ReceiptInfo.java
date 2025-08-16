package com.cms.module.ocr.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 領収書情報を表すエンティティ（receipt_info テーブルに対応）。
 *
 * 用途:
 * - OCRで読み取った領収書の基本情報を保持する。
 * - 経費申請（ExpenseRequest）に紐付けて利用される。
 *
 * 主なフィールド:
 *   - imagePath: 領収書画像ファイルの保存パス
 *   - storeName: 発行元（店名など）
 *   - issueDate: 発行日
 *   - amount   : 金額（JPY整数, BIGINTで保持）
 *   - fullText : OCR全文
 *   - userId   : 登録ユーザー
 *   - status   : 状態（例: "草稿", "確認済", "申請済"）
 *   - createdAt: 登録日時
 */
public class ReceiptInfo {
    /** 主キーID */
    private Long id;

    /** 領収書画像の保存パス（例: /uploads/receipt/xxxx.png） */
    private String imagePath;

    /** 発行元（店名、会社名など） */
    private String storeName;

    /** 発行日 */
    private LocalDate issueDate;

    /** 金額（JPY整数, BIGINTで保持） */
    private Long amount;

    /** OCR全文（元のテキストデータ） */
    private String fullText;

    /** 登録ユーザーID（FK: user.id を想定） */
    private Long userId;

    /** 状態（例: "草稿","確認済","申請済"） */
    private String status;

    /** 作成日時（登録日時） */
    private LocalDateTime createdAt;

    // ================== Getter / Setter ==================

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public String getStoreName() { return storeName; }
    public void setStoreName(String storeName) { this.storeName = storeName; }

    public LocalDate getIssueDate() { return issueDate; }
    public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }

    public Long getAmount() { return amount; }
    public void setAmount(Long amount) { this.amount = amount; }

    public String getFullText() { return fullText; }
    public void setFullText(String fullText) { this.fullText = fullText; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
