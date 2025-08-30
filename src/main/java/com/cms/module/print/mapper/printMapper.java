package com.cms.module.print.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.cms.base.mapper.BaseMapper;
import com.cms.module.employee.entity.Employees;

/**
 * ユーザー情報 Mapper
 */
@Mapper
public interface printMapper extends BaseMapper<Employees, String> {

	List<Map<String, ?>> printInfo();
	
}