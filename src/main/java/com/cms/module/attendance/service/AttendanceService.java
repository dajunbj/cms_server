package com.cms.module.attendance.service;

import java.util.List;
import java.util.Map;

import com.cms.base.service.BaseService;
import com.cms.module.attendance.entity.Attendance;

public interface AttendanceService extends BaseService<Attendance, String> {

    public List<Attendance> getMonthlyData(Map<String, Object> conditions);
    public List<Attendance> getMonthDataByEmployeeId(Map<String, Object> conditions);
}
