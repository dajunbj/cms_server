package com.cms.module.ocr.entity;

import java.time.LocalDateTime;

/**
 * 経費申請 (ExpenseRequest) を表すエンティティ。
 *
 * 用途:
 * - 社員が複数の領収書をまとめて経費申請する単位。
 * - 申請状態（申請済 / 承認済 / 差し戻し）を持ち、承認フローを管理する。
 *
 * 状態遷移例:
 *   - 初期登録時: status = "申請済"
 *   - 承認者が承認 → status = "承認済", approvedBy/approvedAt 更新
 *   - 承認者が差し戻し → status = "差し戻し", rejectReason 記録
 */
public class ExpenseRequest {

    /** 主キーID */
    private Long id;

    /** 申請者ID (FK: user.id を想定) */
    private Long applicantId;

    /** 申請概要（メモや件名など） */
    private String summary;

    /** ステータス（"申請済","承認済","差し戻し"） */
    private String status;

    /** 承認者ID（承認または差し戻しを行ったユーザー） */
    private Long approvedBy;

    /** 承認日時 */
    private LocalDateTime approvedAt;

    /** 合計金額（領収書合計）: JPY整数（BIGINTで保持） */
    private Long totalAmount;

    /** 作成日時（申請日時） */
    private LocalDateTime createdAt;

    /** 差し戻し理由（差し戻し時のみ有効） */
    private String rejectReason;

    // ================== Getter / Setter ==================

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getApplicantId() { return applicantId; }
    public void setApplicantId(Long applicantId) { this.applicantId = applicantId; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Long getApprovedBy() { return approvedBy; }
    public void setApprovedBy(Long approvedBy) { this.approvedBy = approvedBy; }

    public LocalDateTime getApprovedAt() { return approvedAt; }
    public void setApprovedAt(LocalDateTime approvedAt) { this.approvedAt = approvedAt; }

    public Long getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Long totalAmount) { this.totalAmount = totalAmount; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getRejectReason() { return rejectReason; }
    public void setRejectReason(String rejectReason) { this.rejectReason = rejectReason; }
}
