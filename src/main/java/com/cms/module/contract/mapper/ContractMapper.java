package com.cms.module.contract.mapper;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cms.module.contract.entity.CaseBean;
import com.cms.module.contract.entity.ContractBean;
import com.cms.module.contract.form.ContractForm;
import com.cms.module.employee.entity.Employees;

/**
 * ユーザー情報 Mapper
 */
@Mapper
public interface ContractMapper {
	
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
	List<ContractBean> select(Map<String, Object> map);
	
	 /**
     * 插入员工信息
     * 
     * @param ContractBean 员工实体
     * @return 影响的行数
     */
    int insertContract(ContractBean input);
    
    /**
     * 根据ID获取员工详情
     * 
     * @param employeeId 员工ID
     * @return 员工信息
     */
    Employees findById(@Param("employeeId") String employeeId);

    
	 /**
     * 获得员工ID
     * 
     * @param 员工姓名
     * @return 员工ID
     */
    String getEmployeeID(ContractBean bean);
    
    int updateEndDate(ContractForm contract);
    
	 /**
     * 获得契约结束日期
     * 
     * @param 契约ID
     * @return 结束日期
     */
    Date getEndDate(ContractForm contract);
    
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
     * 查找顾客信息
     * 
     * @param 案件ID
     * @return 顾客信息
     */
	List<ContractBean> selectCustomer(Map<String, Object> map);
	
	   /**
     * 删除选中契约
     * 
     * @param 选中条目的契约ID
     * @return 行为结果
     */
	public void deleteSelected(Map<String, Object> conditions);
	
    /**
     * 根据契约ID查找初始化信息
     * 
     * @param 契约ID
     * @return 契约情报
     */
	ContractBean findByContractId (@Param("contract_id") String contract_id);
	
    /**
     * 更新契约
     * 
     * @param 契约更新情报
     * @return 影响的行数
     */
    int updateContract(ContractBean input);
    
    LocalDateTime getUpdateTime(int contract_id);
}









