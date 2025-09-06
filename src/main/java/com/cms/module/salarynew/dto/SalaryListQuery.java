package com.cms.module.salarynew.dto;

import lombok.Data;

@Data
public class SalaryListQuery {
  private Long companyId;
  private Long departmentId;
  private Long employeeId;
  private String salaryMonth; // 'YYYY-MM-01'
  private Integer page = 1;   // 1-based
  private Integer size = 20;
  private String sort = "salaryMonth";
  private String order = "desc"; // asc/desc
}