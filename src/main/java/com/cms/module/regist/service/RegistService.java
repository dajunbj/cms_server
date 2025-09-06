package com.cms.module.regist.service;

import java.util.Map;

import com.cms.module.employee.entity.Employees;

/**
 * 共通サービス層の基底抽象クラス。
 * 基本的なCRUDメソッドを提供し、必要に応じてオーバーライド可能。
 * @param <T> エンティティの型
 */
public interface RegistService {

    /**
     * IDでエンティティを検索
     * @param conditions 検索条件
     * @return ログインユーザ
     */
    public Employees getLoginInfo(String user);
    
    public Map<String, Object> updateLoginInfo(Map<String, Object> conditions);
}
