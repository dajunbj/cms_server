package com.cms.module.salarynew.dto;

import lombok.Data;
@Data
public class SalaryDetailDto {
  public Integer id;
  public Integer employee_id;
  public String  employee_code;   // 便于抬头显示
  public String  employee_name;   // 便于抬头显示
  public String  salary_month;    // 'YYYY-MM-01'

  // ===== 支給 =====
  public Integer base_salary;
  public Integer position_allowance;
  public Integer commuting_allowance;
  public Integer overtime_allowance;
  public Integer family_allowance;
  public Integer qualification_allowance;
  // 可选扩展
  public Integer housing_allowance;
  public Integer midnight_allowance;
  public Integer holiday_allowance;

  public Integer total_payment;

  // ===== 控除 =====
  public Integer health_insurance;
  public Integer welfare_pension;
  public Integer employment_insurance;
  public Integer nursing_insurance;
  public Integer income_tax;
  public Integer resident_tax;
  public Integer total_deduction;

  public Integer net_payment;

  // ===== 勤怠（表里已有 or 可选追加）=====
  public Integer working_days;
  public Integer overtime_days;
  public Double  overtime_hours; // 小时
  // 可选扩展
  public Integer absent_days;
  public Integer special_leave_days;
  public Integer paid_leave_days;
  public Integer paid_leave_remain_days;
  public Double  working_hours;
  public Double  midnight_hours;
  public Double  holiday_work_hours;
  public Double  late_early_hours;
}