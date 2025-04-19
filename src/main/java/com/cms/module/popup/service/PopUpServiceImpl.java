package com.cms.module.popup.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.module.contract.entity.CaseBean;
import com.cms.module.popup.entity.DepartmentBean;
import com.cms.module.popup.mapper.PopUpMapper;

/**
 * Employee のサービスクラス
 */
@Service
public class PopUpServiceImpl implements PopUpService {

	/**複数テーブル処理*/
	@Autowired
	PopUpMapper mapper;
	
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
     * 检索部门pop
     *
     * @param customer_name
     * @return 顾客详情
     */
	public Map<String, Object> findDepartmentByName(Map<String, Object>conditions){
		List<DepartmentBean> results = mapper.selectDepartmentByName(conditions);
		Map<String, Object> result = new HashMap<>();
		result.put("data", results);
		return result;
	}
}










