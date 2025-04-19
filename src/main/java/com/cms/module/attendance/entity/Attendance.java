package com.cms.module.attendance.entity ;

import lombok.Data;

@Data
public class Attendance {
    private Integer sheet_id;
    private Integer employee_id;
    private Integer contract_id;
    private String date;
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
    private Boolean is_modified; // 新增字段：判断是否需要更新
    private String employeeName;
}