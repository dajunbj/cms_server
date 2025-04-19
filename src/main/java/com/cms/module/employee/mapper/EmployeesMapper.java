package com.cms.module.employee.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.cms.base.mapper.BaseMapper;
import com.cms.module.employee.entity.Employees;

/**
 * ユーザー情報 Mapper
 */
@Mapper
public interface EmployeesMapper extends BaseMapper<Employees, String> {

}