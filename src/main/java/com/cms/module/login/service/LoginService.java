package com.cms.module.login.service;

import java.util.Map;

/**
 * 共通サービス層の基底抽象クラス。
 * 基本的なCRUDメソッドを提供し、必要に応じてオーバーライド可能。
 * @param <T> エンティティの型
 */
public interface LoginService {

    /**
     * IDでエンティティを検索
     * @param conditions 検索条件
     * @return エンティティオブジェクト
     */
    public int searchLoginInfo(Map<String, Object> conditions);

}
