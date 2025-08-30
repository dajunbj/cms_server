package com.cms.module.salarynew.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cms.module.salarynew.dto.SalaryDetailDto;

@Mapper
public interface SalaryMapper {

    // 社員一覧
    List<Map<String,Object>> listEmployees();

    // 給与明細を1件取得
    SalaryDetailDto getDetail(@Param("employeeId") Integer employeeId,
                              @Param("salaryMonth") String salaryMonth);
}