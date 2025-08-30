package com.cms.module.invite.service;

import java.util.Map;

/**
 * 共通サービス層の基底抽象クラス。
 * 基本的なCRUDメソッドを提供し、必要に応じてオーバーライド可能。
 * @param <T> エンティティの型
 */
public interface InviteService {
	
	public int registData(Map<String, Object> BasicInfo);
}
