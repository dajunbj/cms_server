package com.cms.module.contract.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.module.contract.entity.CaseBean;
import com.cms.module.contract.entity.ContractBean;
import com.cms.module.contract.form.ContractForm;
import com.cms.module.contract.mapper.ContractMapper;
import com.cms.module.employee.entity.Employee;

/**
 * Employee のサービスクラス
 */
@Service
public class ContractServiceImpl implements ContractService {

	/**複数テーブル処理*/
	@Autowired
	ContractMapper mapper;
	
    public Map<String, Object> findAllWithPagination(Map<String, Object> conditions) {
    	int recordCount = mapper.selectCount(conditions);
    	List<ContractBean> results = mapper.select(conditions);
        Map<String, Object> result = new HashMap<>();
        result.put("data", results); // 当前页数据
        result.put("total", recordCount); // 总条目数
        return result;

    }

	@Override
	public void saveContract(ContractBean input) {

		mapper.insertContract(input);
	}
	
	  /**
     * 根据ID获取员工详情
     *
     * @param id 员工ID
     * @return 员工详情
     */
    @Override
    public Employee findById(String id) {
        return mapper.findById(id);
    }


	public void loginSearch(Employee employee) {
		
	}
    
	public String getEmployeeID(ContractBean bean) {
		mapper.getEmployeeID(bean);
		return null;
	}
	
    /**
     * 更新终了日
     *
     * @param end_date
     * @return 更新是否成功/错误信息
     */
	public void finishManual(ContractForm contract) {
		mapper.updateEndDate(contract);
	}
	
    /**
     * 获得现有终了日
     *
     * @param end_date
     * @return 更新是否成功/错误信息
     */
	public Date getEndDate(ContractForm contract) {
		Date endDate = mapper.getEndDate(contract);
		return endDate;
	}
	
	/**
     * 检索案件
     *
     * @param case_title
     * @return 案件详情
     */
	public Map<String, Object> findCase(Map<String, Object>conditions){
		List<CaseBean> results = mapper.selectCase(conditions);
		Map<String, Object> result = new HashMap<>();
		result.put("data", results);
		return result;
	}
	
	/**
     * 检索员工
     *
     * @param name
     * @return 员工详情
     */
	public Map<String, Object> findEmployee(Map<String, Object>conditions){
		List<CaseBean> results = mapper.selectEmployee(conditions);
		Map<String, Object> result = new HashMap<>();
		result.put("data", results);
		return result;
	}
	
	/**
     * 检索顾客pop
     *
     * @param customer_name
     * @return 顾客详情
     */
	public Map<String, Object> findCustomerByName(Map<String, Object>conditions){
		List<CaseBean> results = mapper.selectCustomerByName(conditions);
		Map<String, Object> result = new HashMap<>();
		result.put("data", results);
		return result;
	}
	
	/**
     * 检索顾客信息
     *
     * @param custome_id
     * @return 顾客详情
     */
	public Map<String, Object> findCustomer(Map<String, Object>conditions){
		List<ContractBean> results = mapper.selectCustomer(conditions);
		Map<String, Object> result = new HashMap<>();
		result.put("data", results);
		return result;
	}
	
	public Map<String, Object> deleteSelected(Map<String, Object>conditions){
		Map<String, Object> result = new HashMap<>();
		try {
			mapper.deleteSelected(conditions);
			result.put("message", "削除成功しました。");
			result.put("status", "success");
		}
		catch(Exception e){
			result.put("message", "削除失敗しました。"+ e.getMessage());
			result.put("status", "error");
			System.out.println(e);
		}
		return result;
	}
	
	  /**
     * 根据ID获取契约信息
     *
     * @param id 契约ID
     * @return 契约详情
     */
    @Override
    public ContractBean findByContractId(String contract_id) {
        return mapper.findByContractId(contract_id);
    }
    
    /**
     * 更新员工信息
     *
     * @param employee 员工实体
     * @return 更新是否成功
     */
    @Override
    public boolean updateContract(ContractBean input) {
    	LocalDateTime time_now = LocalDateTime.now();
        input.setUpdate_time(time_now);
        return mapper.updateContract(input) > 0;
    }

	@Override
	public LocalDateTime getUpdateTime(int contract_id) {
		return mapper.getUpdateTime(contract_id);
	}
    
}










