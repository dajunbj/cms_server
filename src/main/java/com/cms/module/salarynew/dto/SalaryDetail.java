package com.cms.module.salarynew.dto;
import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class SalaryDetail {
  private Long id;
  private Long companyId;
  private String companyName;
  private Long departmentId;
  private String departmentName;
  private Long employeeId;
  private String employeeCode;
  private String employeeName;
  private LocalDate salaryMonth;

  private BigDecimal totalPayment;
  private BigDecimal totalDeduction;
  private BigDecimal netPayment;

  // 明细字段（保留，明细页会用到）
  private BigDecimal baseSalary;
  private BigDecimal overtimeAllowance;
  private BigDecimal midnightAllowance;
  private BigDecimal holidayAllowance;
  private BigDecimal qualificationAllowance;
  private BigDecimal positionAllowance;
  private BigDecimal housingAllowance;
  private BigDecimal commutingAllowance;

  private BigDecimal healthInsurance;
  private BigDecimal nursingInsurance;
  private BigDecimal welfarePension;
  private BigDecimal employmentInsurance;
  private BigDecimal incomeTax;
  private BigDecimal residentTax;
}