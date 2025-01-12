package com.cms.module.employee.service;

import java.util.Map;

import com.cms.module.employee.entity.Employee;

/**
 * 共通サービス層の基底抽象クラス。
 * 基本的なCRUDメソッドを提供し、必要に応じてオーバーライド可能。
 * @param <T> エンティティの型
 */
public interface EmployeeService {

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
    public void saveEmployee(Employee employee);
    
    Employee findById(String id);
    
    boolean updateEmployee(Employee employee);
}
