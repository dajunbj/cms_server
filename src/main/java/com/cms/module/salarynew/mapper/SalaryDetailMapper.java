package com.cms.module.salarynew.mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cms.module.salarynew.dto.SalaryListItemDTO;

@Mapper
public interface SalaryDetailMapper {

  long countList(@Param("companyId") Long companyId,
                 @Param("departmentId") Long departmentId,
                 @Param("employeeId") Long employeeId,
                 @Param("salaryMonth") LocalDate salaryMonth);

  List<SalaryListItemDTO> selectList(@Param("companyId") Long companyId,
                                     @Param("departmentId") Long departmentId,
                                     @Param("employeeId") Long employeeId,
                                     @Param("salaryMonth") LocalDate salaryMonth,
                                     @Param("orderBy") String orderBy,
                                     @Param("offset") int offset,
                                     @Param("limit") int limit);

  /** 选项下拉：company/department/employee */
  List<Map<String,Object>> distinctCompanies();
  List<Map<String,Object>> distinctDepartments();
  List<Map<String,Object>> distinctEmployees();
}