package com.cms.module.ocr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 経費申請と領収書の紐付けテーブル（expense_receipt_link）用のMapper。
 *
 * 主な用途:
 * - 経費申請（ExpenseRequest）と複数の領収書（ReceiptInfo）を一括で紐付ける。
 */
@Mapper
public interface ExpenseReceiptLinkMapper {

    /**
     * 経費申請と複数の領収書を一括登録する。
     *
     * @param expenseRequestId 経費申請ID
     * @param receiptIds       紐付ける領収書IDリスト
     * @return 登録件数
     */
    int batchInsert(@Param("expenseRequestId") Long expenseRequestId,
                    @Param("receiptIds") List<Long> receiptIds);
}
