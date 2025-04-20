package com.cms.module.attendance.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.cms.base.mapper.BaseMapper;
import com.cms.module.attendance.entity.Attendance;

@Mapper
public interface AttendanceMapper  extends BaseMapper<Attendance, String> {

    /**
     * 统计符合条件的记录数
     */
	List<Attendance>  getMonthlyData(Map<String, Object> conditions);
}
