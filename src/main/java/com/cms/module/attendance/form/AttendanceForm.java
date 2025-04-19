package com.cms.module.attendance.form;


import java.util.List;

import com.cms.module.attendance.entity.Attendance;

import lombok.Data;

@Data
public class AttendanceForm {

    private Integer employee_id;         // 上方输入的社員ID
    private Integer contract_id;         // 契約ID
    private String month;                // 勤怠年月（yyyy-MM）

    private List<Attendance> attendanceList; // 勤怠明細リスト
}