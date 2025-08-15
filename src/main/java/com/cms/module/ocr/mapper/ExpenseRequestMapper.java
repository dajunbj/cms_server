package com.cms.module.ocr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cms.module.ocr.entity.ExpenseRequest;

@Mapper
public interface ExpenseRequestMapper {
	int insert(ExpenseRequest req);

	Long sumReceiptAmounts(@Param("ids") List<Long> ids);
}