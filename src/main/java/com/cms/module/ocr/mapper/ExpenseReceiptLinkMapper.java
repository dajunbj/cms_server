package com.cms.module.ocr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ExpenseReceiptLinkMapper {
	int batchInsert(@Param("expenseRequestId") Long expenseRequestId, @Param("receiptIds") List<Long> receiptIds);
}