package com.cms.module.popup.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.cms.module.contract.entity.CaseBean;
import com.cms.module.popup.entity.DepartmentBean;

/**
 * ユーザー情報 Mapper
 */
@Mapper
public interface PopUpMapper {
	
	 /**
     * 查找案件信息
     * 
     * @param 案件标题
     * @return 案件信息
     */
	List<CaseBean> selectCase(Map<String, Object> map);
	
	 /**
     * 查找员工信息
     * 
     * @param 员工姓名
     * @return 员工信息
     */
	List<CaseBean> selectEmployee(Map<String, Object> map);
	
	 /**
     * 查找顾客信息pop
     * 
     * @param 顾客姓名
     * @return 顾客信息
     */
	List<CaseBean> selectCustomerByName(Map<String, Object> map);
	
	 /**
     * 查找部门信息pop
     * 
     * @param 顾客姓名
     * @return 顾客信息
     */
	List<DepartmentBean> selectDepartmentByName(Map<String, Object> map);
	
}









