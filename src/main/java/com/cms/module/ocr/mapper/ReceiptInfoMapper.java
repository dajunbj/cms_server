package com.cms.module.ocr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cms.module.ocr.dto.ReceiptInfoListItem;
import com.cms.module.ocr.dto.ReceiptQueryCondition;
import com.cms.module.ocr.entity.ReceiptInfo;

@Mapper
public interface ReceiptInfoMapper {

	int insert(ReceiptInfo info);

	// 分页查询
	List<ReceiptInfoListItem> queryByCondition(@Param("cond") ReceiptQueryCondition cond);

	// 统计总数
	int countByCondition(@Param("cond") ReceiptQueryCondition cond);

	// 复用现有的 ReceiptInfoMapper，增加两个方法（更新状态 & 校验可申请）
	int updateStatusAppliedBatch(@Param("ids") List<Long> ids);

	int countNotApplyable(@Param("ids") List<Long> ids); // 非 草稿/確認済 的条数

}
