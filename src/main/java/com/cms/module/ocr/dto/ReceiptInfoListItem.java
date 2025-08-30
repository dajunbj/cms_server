package com.cms.module.ocr.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 領収書情報リスト用 DTO
 * 
 * 承認画面や申請詳細画面で一覧表示するための軽量オブジェクト。
 * DBの ReceiptInfo エンティティの一部項目を抜粋して保持する。
 *
 * 使用例:
 * - 経費申請の詳細画面で「添付領収書リスト」を表示する
 * - 一覧画面の領収書参照
 */
public class ReceiptInfoListItem {

    /** 領収書ID（主キー） */
    private Long id;

    /** 領収書画像の保存パス（相対 or 絶対パス） */
    private String imagePath;

    /** 発行先（OCR抽出／手入力） */
    private String issuer;

    /** 発行日（yyyy-MM-dd） */
    private LocalDate issueDate;

    /** 金額（円単位の整数。DBはBIGINT、JavaはLongで保持） */
    private Long amount;

    /** ステータス（例: "草稿", "確認済", "申請済", "承認済", "差戻し"） */
    private String status;

    /** 登録日時（システムに保存された時刻） */
    private LocalDateTime createdAt;


    // ================== Getter / Setter ==================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
