package com.cms.module.attendance.form;


import java.util.List;

import com.cms.module.attendance.entity.Attendance;

import lombok.Data;

@Data
public class AttendanceForm {

    //社員ID
    private Integer employee_id;
    //社員名
    private String employeeName;
    //契約ID
    private Integer contract_id;
    //勤怠年月（yyyy-MM）
    private String month;                

    private List<Attendance> attendanceList; // 勤怠明細リスト
}