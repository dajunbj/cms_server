package com.cms.module.print.service;

import java.util.List;
import java.util.Map;

/**
 * 共通サービス層の基底抽象クラス。
 * 基本的なCRUDメソッドを提供し、必要に応じてオーバーライド可能。
 * @param <T> エンティティの型
 */
public interface printService {

    /**
     * IDでエンティティを検索
     * @param conditions 検索条件
     * @return ログインユーザ
     */
	//检索案件信息
	public List<Map<String, ?>> printInfo();
    
}
