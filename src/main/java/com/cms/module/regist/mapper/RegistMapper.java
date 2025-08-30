package com.cms.module.regist.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.cms.base.mapper.BaseMapper;
import com.cms.module.employee.entity.Employees;

/**
 * ユーザー情報 Mapper
 */
@Mapper
public interface RegistMapper extends BaseMapper<Employees, String> {

	 int updateLoginInfo(Map<String, Object> conditions);
}