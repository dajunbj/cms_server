package com.cms.module.popup.service;

import java.util.Map;

/**
 * 共通サービス層の基底抽象クラス。
 * 基本的なCRUDメソッドを提供し、必要に応じてオーバーライド可能。
 * @param <T> エンティティの型
 */
public interface PopUpService {
    //检索案件信息
    public Map<String, Object> findCase(Map<String, Object>conditions);
    
    //检索员工信息
    public Map<String, Object> findEmployee(Map<String, Object>conditions);
    
    //检索顾客信息popup
    public Map<String, Object> findCustomerByName(Map<String, Object>conditions);
    
    //检索部门信息popup
    public Map<String, Object> findDepartmentByName(Map<String, Object>conditions);
}











