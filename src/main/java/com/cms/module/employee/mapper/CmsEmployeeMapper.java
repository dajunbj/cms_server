package com.cms.module.employee.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cms.module.employee.entity.Employee;

/**
 * ユーザー情報 Mapper
 */
@Mapper
public interface CmsEmployeeMapper {
	
	/**
	 * レコード件数取得
	 * 
	 * @param conditions 条件
	 * @return レコード件数
	 */
	int selectCount(Map<String, Object> conditions);

	/**
	 * ユーザー情報検索
	 * 
	 * @param conditions 条件
	 * @return 社員情報
	 */
	List<Employee> select(Map<String, Object> map);
	
	 /**
     * 插入员工信息
     * 
     * @param employee 员工实体
     * @return 影响的行数
     */
    int insertEmployee(Employee employee);
    
    /**
     * 根据ID获取员工详情
     * 
     * @param employeeId 员工ID
     * @return 员工信息
     */
    Employee findById(@Param("employeeId") String employeeId);

    /**
     * 更新员工信息
     * 
     * @param employee 员工实体
     * @return 影响的行数
     */
    int updateEmployee(Employee employee);
}