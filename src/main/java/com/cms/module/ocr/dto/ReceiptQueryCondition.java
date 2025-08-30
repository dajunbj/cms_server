package com.cms.module.ocr.dto;

/**
 * 領収書検索用 条件DTO
 *
 * 一覧API (/api/ocr/receipts/list) の検索条件を保持するためのオブジェクト。
 * Controller → Service → Mapper の間で利用される。
 *
 * 主に以下の用途:
 * - 領収書一覧画面のページネーション
 * - 店舗名／ステータスによる絞り込み
 */
public class ReceiptQueryCondition {

    /** 取得開始位置 (例: (page-1) * size) */
    private int offset;

    /** 取得件数 (例: size, デフォルトは10) */
    private int limit;

    /** 発行先の部分一致検索用 */
    private String issuer;

    /** ステータスでの絞り込み (草稿/確認済/申請済/承認済/差戻し) */
    private String status;

    // ================== Getter / Setter ==================

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
