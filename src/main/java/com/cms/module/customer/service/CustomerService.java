package com.cms.module.customer.service;

import java.time.LocalDateTime;
import java.util.Map;

import com.cms.module.customer.entity.CustomerBean;
import com.cms.module.customer.entity.ResponsibleBean;
import com.cms.module.customer.form.CustomerForm;
import com.cms.module.customer.form.ResponsibleForm;

/**
 * 共通サービス層の基底抽象クラス。
 * 基本的なCRUDメソッドを提供し、必要に応じてオーバーライド可能。
 * @param <T> エンティティの型
 */
public interface CustomerService {

    /**
     * 顾客检索
     * @param conditions 検索条件
     * @return エンティティオブジェクト
     */
    public Map<String, Object> findAllCustomerWithPagination(Map<String, Object> conditions);
    
    /**
     * 责任者检索
     * @param conditions 検索条件
     * @return エンティティオブジェクト
     */
    public Map<String, Object> findAllResponsibleWithPagination(Map<String, Object> conditions);
    
    //根据顾客ID取得信息（初期化用）
    CustomerBean findByCustomerId (String customer_id);
    
    //根据责任者ID取得信息（初期化用）
    ResponsibleBean findByResponsibleId (String responsible_id);
    
    /**
     *   检索更新日期进行乐观check
     *   @param id,type
     *   @return 更新日期
     */
    public LocalDateTime getUpdate(int id , String type);
    
    //删除选中条目
    public Map<String, Object> deleteSelected(Map<String, Object>conditions);
    
    //登录顾客信息
    public int registerCustomer(CustomerForm input);
    
    //登录责任者信息
    public void registerResponsible(ResponsibleForm input);
    
    //更新顾客信息
    boolean updateCustomer(CustomerBean input);
    
    //更新责任者信息
    boolean updateResponsible(ResponsibleBean input);
}











