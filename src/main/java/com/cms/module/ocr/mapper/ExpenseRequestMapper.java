package com.cms.module.ocr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cms.module.ocr.dto.ReceiptInfoListItem;
import com.cms.module.ocr.entity.ExpenseRequest;

/**
 * 経費申請（expense_request テーブル）用のMapper。
 *
 * 主な用途:
 * - 経費申請の新規登録
 * - 一覧・件数取得
 * - 詳細情報の取得（領収書含む）
 * - 承認／差戻し処理
 */
@Mapper
public interface ExpenseRequestMapper {

    // ========== 経費申請（申請画面用） ==========

    /**
     * 経費申請を新規登録する。
     * useGeneratedKeys=true によりIDは自動採番。
     */
    int insert(ExpenseRequest req);

    /**
     * 領収書IDリストに基づき合計金額を計算する。
     *
     * @param ids 領収書IDリスト
     * @return 合計金額
     */
    Long sumReceiptAmounts(@Param("ids") List<Long> ids);

    // ========== 一覧取得（承認画面用） ==========

    /**
     * 経費申請の一覧を取得する（ページング対応）。
     *
     * @param status      状態フィルタ（null可）
     * @param offset      取得開始位置
     * @param limit       最大取得件数
     * @param applicantId 申請者ID（null可）
     */
    List<ExpenseRequest> list(@Param("status") String status,
                              @Param("offset") int offset,
                              @Param("limit") int limit,
                              @Param("applicantId") Long applicantId);

    /** 一覧総件数を取得 */
    int count(@Param("status") String status,
              @Param("applicantId") Long applicantId);

    // ========== 詳細（領収書含む） ==========

    /** 指定IDの経費申請を取得 */
    ExpenseRequest findById(@Param("id") Long id);

    /** 経費申請に紐付く領収書一覧を取得 */
    List<ReceiptInfoListItem> findReceiptsByRequestId(@Param("requestId") Long requestId);

    // ========== 承認／差戻し ==========

    /**
     * 承認処理を実行する。
     *
     * @param id       経費申請ID
     * @param approver 承認者ID
     * @return 更新件数（0なら失敗）
     */
    int approve(@Param("id") Long id, @Param("approver") Long approver);

    /**
     * 差戻し処理を実行する。
     *
     * @param id       経費申請ID
     * @param approver 承認者ID
     * @param reason   差戻し理由
     * @return 更新件数（0なら失敗）
     */
    int reject(@Param("id") Long id,
               @Param("approver") Long approver,
               @Param("reason") String reason);
}
