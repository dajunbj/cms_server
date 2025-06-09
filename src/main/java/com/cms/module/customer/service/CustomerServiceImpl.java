package com.cms.module.customer.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.module.customer.entity.CustomerBean;
import com.cms.module.customer.entity.ResponsibleBean;
import com.cms.module.customer.form.CustomerForm;
import com.cms.module.customer.form.ResponsibleForm;
import com.cms.module.customer.mapper.CustomerMapper;

/**
 * Employee のサービスクラス
 */
@Service
public class CustomerServiceImpl implements CustomerService {

	/**複数テーブル処理*/
	@Autowired
	CustomerMapper mapper;
	
	/**
     * 顾客检索
     * @param conditions 検索条件
     * @return エンティティオブジェクト
     */
    public Map<String, Object> findAllCustomerWithPagination(Map<String, Object> conditions) {
    	int recordCount = mapper.selectCustomerCount(conditions);
    	List<CustomerBean> results = mapper.selectCustomer(conditions);
        Map<String, Object> result = new HashMap<>();
        result.put("data", results); // 当前页数据
        result.put("total", recordCount); // 总条目数
        return result;

    }
    
    /**
     * 责任者检索
     * @param conditions 検索条件
     * @return エンティティオブジェクト
     */
    public Map<String, Object> findAllResponsibleWithPagination(Map<String, Object> conditions) {
    	int recordCount = mapper.selectResponsibleCount(conditions);
    	List<ResponsibleBean> results = mapper.selectResponsible(conditions);
        Map<String, Object> result = new HashMap<>();
        result.put("data", results); // 当前页数据
        result.put("total", recordCount); // 总条目数
        return result;

    }
    
	  /**
     * 根据ID获取顾客信息
     *
     * @param id 顾客ID
     * @return 顾客详情
     */
    @Override
    public CustomerBean findByCustomerId(String customer_id) {
        return mapper.findByCustomerId(customer_id);
    }
    
	  /**
     * 根据ID获取责任者信息
     *
     * @param id 责任者ID
     * @return 责任者详情
     */
    @Override
    public ResponsibleBean findByResponsibleId(String responsible_id) {
        return mapper.findByResponsibleId(responsible_id);
    }

    /**
     *   检索更新日期进行乐观check
     *   @param id,type
     *   @return 更新日期
     */
    @Override
    public LocalDateTime getUpdate(int id , String type) {
    	LocalDateTime update_now = null;
    	if(type == "customer") {
    	    update_now = mapper.getCustomerUpdate(id);
    	}
    	if(type == "responsible") {
    		update_now = mapper.getResponsibleUpdate(id);
    	}
		return update_now;
	}
    
	/**
     * 删除处理
     *
     * @param contract_id
     * @return 处理结果
     */
	public Map<String, Object> deleteSelected(Map<String, Object>conditions){
		Map<String, Object> result = new HashMap<>();
		String type = (String)conditions.get("type");
		if(type == "customer") {
			try {
				mapper.deleteSelectedCustomer(conditions);
				result.put("message", "削除成功しました。");
				result.put("status", "success");
			}
			catch(Exception e){
				result.put("message", "削除失敗しました。"+ e.getMessage());
				result.put("status", "error");
				System.out.println(e);
			}
	    }
		else {
			try {
				mapper.deleteSelectedResponsible(conditions);
				result.put("message", "削除成功しました。");
				result.put("status", "success");
			}
			catch(Exception e){
				result.put("message", "削除失敗しました。"+ e.getMessage());
				result.put("status", "error");
				System.out.println(e);
			}
		}
		return result;
	}
	
	/**
     * 登录顾客信息
     *
     * @param input
     * @return null
     */
	@Override
	public int registerCustomer(CustomerForm input) {

		mapper.registerCustomer(input);
		return input.getCustomer_id();
	}
	
	/**
     * 登录责任者信息
     *
     * @param input
     * @return null
     */
	@Override
	public void registerResponsible(ResponsibleForm input) {

		mapper.registerResponsible(input);
	}
	
    /**
     * 更新顧客信息
     *
     * @param customerbean 顾客实体
     * @return 更新是否成功
     */
    @Override
    public boolean updateCustomer(CustomerBean input) {
        return mapper.updateCustomer(input) > 0;
    }
    
    /**
     * 更新责任者信息
     *
     * @param responsiblebean 责任者实体
     * @return 更新是否成功
     */
    @Override
    public boolean updateResponsible(ResponsibleBean input) {
        return mapper.updateResponsible(input) > 0;
    }

}










