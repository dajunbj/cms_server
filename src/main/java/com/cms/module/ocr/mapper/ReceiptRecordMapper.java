package com.cms.module.ocr.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cms.module.ocr.entity.ReceiptRecord;

/**
 * MyBatisの領収書データ操作インターフェース。
 */
@Mapper
public interface ReceiptRecordMapper {
    /**
     * レコードをDBに挿入する
     */
    void insert(ReceiptRecord record);

    /**
     * 指定されたreceiptKeyが既に存在するかを確認
     */
    boolean existsByReceiptKey(@Param("receiptKey") String receiptKey);
}
