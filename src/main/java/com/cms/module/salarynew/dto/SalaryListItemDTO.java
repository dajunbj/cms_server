package com.cms.module.salarynew.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class SalaryListItemDTO {
  private Long id;
  private Long companyId;
  private String companyName;
  private Long departmentId;
  private String departmentName;
  private Long employeeId;
  private String employeeCode;
  private String employeeName;
  private String salaryMonth; // 'YYYY-MM-01'
  private BigDecimal totalPayment;
  private BigDecimal totalDeduction;
  private BigDecimal netPayment;
}