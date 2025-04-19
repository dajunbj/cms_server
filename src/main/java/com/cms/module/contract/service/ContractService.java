package com.cms.module.contract.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

import com.cms.module.contract.entity.ContractBean;
import com.cms.module.contract.form.ContractForm;
import com.cms.module.employee.entity.Employee;

/**
 * 共通サービス層の基底抽象クラス。
 * 基本的なCRUDメソッドを提供し、必要に応じてオーバーライド可能。
 * @param <T> エンティティの型
 */
public interface ContractService {

    /**
     * IDでエンティティを検索
     * @param conditions 検索条件
     * @return エンティティオブジェクト
     */
    public Map<String, Object> findAllWithPagination(Map<String, Object> conditions);
    /**
     * IDでエンティティを検索
     * @param conditions 検索条件
     * @return エンティティオブジェクト
     */
    public void saveContract(ContractBean input);
    
    Employee findById(String id);
    
    
    /**
     *   根据传入用户名与密码进行数据库比对并返回身份组
     *   @param conditions 检索条件
     *   @return 能否登录+身份
     */
    public void loginSearch(Employee employee);
    
    //提前结束
    public void finishManual(ContractForm contract);
    
    //取得现在结束日
    public Date getEndDate(ContractForm contract);
    
    //检索案件信息
    public Map<String, Object> findCase(Map<String, Object>conditions);
    
    //检索员工信息
    public Map<String, Object> findEmployee(Map<String, Object>conditions);
    
    //检索顾客信息popup
    public Map<String, Object> findCustomerByName(Map<String, Object>conditions);
    
    //检索顾客信息
    public Map<String, Object> findCustomer(Map<String, Object>conditions);
    
    //删除选中条目
    public Map<String, Object> deleteSelected(Map<String, Object>conditions);
    
    //根据契约ID取得信息（初期化用）
    ContractBean findByContractId (String id);
    
    //更新契约
    boolean updateContract(ContractBean input);
    
    LocalDateTime getUpdateTime(int id);
}











