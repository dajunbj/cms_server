package com.cms.module.login.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.cms.base.mapper.BaseMapper;
import com.cms.module.employee.entity.Employees;

/**
 * ユーザー情報 Mapper
 */
@Mapper
public interface LoginMapper extends BaseMapper<Employees, String> {

	
}