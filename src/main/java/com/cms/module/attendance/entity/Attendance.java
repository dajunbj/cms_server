package com.cms.module.attendance.entity ;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Attendance {
	
    private Integer sheet_id;
    private Integer employee_id;
    private Integer contract_id;
    private String workday;
    private String start_time;
    private String end_time;
    private Double working_hours;
    private Double break_hours;
    private String attendance_type;
    private Double overtime_hours;
    private Double deducted_hours;
    private Boolean is_leave_used;
    private Integer holiday_used_id;
    private String notes;
    private Boolean is_modified; 
    
    // 社員名
    private String employeeName;
    // 勤務月
    private String workmonth;
    // 月別勤務時間
    private BigDecimal workhours;
    // 勤務日数
    private int workday_count;
}