package com.cms.module.ocr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cms.module.ocr.dto.ReceiptInfoListItem;
import com.cms.module.ocr.dto.ReceiptQueryCondition;
import com.cms.module.ocr.entity.ReceiptInfo;

/**
 * 領収書情報（receipt_info テーブル）用 Mapper。
 *
 * 機能:
 *  - 新規登録
 *  - 条件検索・件数取得（ページング）
 *  - 一括状態更新
 *  - 申請可否チェック
 *  - ユーザー別一覧（Excel 出力用）
 *  - メタ更新（項目のみ）
 *  - 保存取消（削除）
 */
@Mapper
public interface ReceiptInfoMapper {

    /** 新規登録（useGeneratedKeys で id を受け取る） */
    int insert(ReceiptInfo info);

    // ===== 一覧検索・件数 =====

    /** 条件検索（cond: offset, limit, storeName or issuer, status） */
    List<ReceiptInfoListItem> queryByCondition(@Param("cond") ReceiptQueryCondition cond);

    /** 条件件数 */
    int countByCondition(@Param("cond") ReceiptQueryCondition cond);

    // ===== 状態更新・可否チェック =====

    /** 指定 ID を一括で「申請済」に更新（草稿／確認済のみ対象） */
    int updateStatusAppliedBatch(@Param("ids") List<Long> ids);

    /** 指定 ID のうち申請不可の件数（草稿／確認済 以外） */
    int countNotApplyable(@Param("ids") List<Long> ids);

    // ===== ユーザー別一覧（Excel 用） =====

    /** ユーザー別一覧 */
    List<ReceiptInfoListItem> findByUserId(@Param("userId") Long userId);

    // ===== 追加：メタ更新・削除 =====

    /** メタ更新（画像は更新しない） */
    int updateMeta(@Param("e") ReceiptInfo info);

    /** 保存取消（論理・物理は SQL 次第。ここでは物理削除例） */
    int deleteByIdAndUser(@Param("id") Long id, @Param("userId") Long userId);
}
