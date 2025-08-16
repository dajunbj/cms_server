package com.cms.module.ocr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cms.module.ocr.dto.ReceiptInfoListItem;
import com.cms.module.ocr.dto.ReceiptQueryCondition;
import com.cms.module.ocr.entity.ReceiptInfo;

/**
 * 領収書情報（receipt_info テーブル）用のMapper。
 *
 * 主な用途:
 * - OCR読み取り結果の登録
 * - 一覧・件数取得（検索条件あり）
 * - 状態更新（申請済など）
 */
@Mapper
public interface ReceiptInfoMapper {

    /** 領収書情報を新規登録 */
    int insert(ReceiptInfo info);

    // ========== 一覧検索（OCR結果確認画面用） ==========

    /**
     * 条件に基づき領収書一覧を取得する（ページング対応）。
     *
     * @param cond 検索条件（offset, limit, storeName, status）
     */
    List<ReceiptInfoListItem> queryByCondition(@Param("cond") ReceiptQueryCondition cond);

    /** 条件に一致する件数を取得 */
    int countByCondition(@Param("cond") ReceiptQueryCondition cond);

    // ========== 状態更新・申請可否チェック ==========

    /**
     * 指定した領収書をまとめて「申請済」に更新。
     *
     * @param ids 領収書IDリスト
     * @return 更新件数
     */
    int updateStatusAppliedBatch(@Param("ids") List<Long> ids);

    /**
     * 指定IDの中で「申請不可」状態の件数を返す。
     * （申請可：草稿／確認済, それ以外は不可）
     *
     * @param ids 領収書IDリスト
     * @return 不可件数
     */
    int countNotApplyable(@Param("ids") List<Long> ids);
}
