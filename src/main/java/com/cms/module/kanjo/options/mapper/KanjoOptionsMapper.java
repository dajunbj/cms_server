package com.cms.module.kanjo.options.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cms.module.kanjo.options.dto.KanjoCategoryRow;

@Mapper
public interface KanjoOptionsMapper {

    // ✅ 使用视图 v_acct_category_effective
    List<KanjoCategoryRow> selectEffectiveByCompany(@Param("companyId") Long companyId);

    /* ===== 内联版本（已注释；当前不使用视图时再启用） =====
    List<KanjoCategoryRow> selectEffectiveByCompanyInline(@Param("companyId") Long companyId);
    ===== end ===== */

    /* ===== 懒加载接口（整套已注释；当前不用） =====
    List<KanjoCategoryRow> selectRoots(@Param("companyId") Long companyId);
    List<KanjoCategoryRow> selectChildrenByParentCode(@Param("companyId") Long companyId,
                                                      @Param("parentCode") String parentCode);
    Integer existsChildren(@Param("companyId") Long companyId, @Param("code") String code);
    ===== end ===== */
}
