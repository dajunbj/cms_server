package com.cms.module.attendance.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.cms.base.mapper.BaseMapper;
import com.cms.module.attendance.entity.Attendance;

@Mapper
public interface AttendanceMapper  extends BaseMapper<Attendance, String> {

}
