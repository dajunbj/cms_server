package com.cms.api.service;

import java.util.List;

import com.cms.api.entity.ResponseBean;
import com.cms.api.form.ApiForm;

/**
 * 共通サービス層の基底抽象クラス。
 * 基本的なCRUDメソッドを提供し、必要に応じてオーバーライド可能。
 * @param <T> エンティティの型
 */
public interface ApiService {

    public int shareCheck(int id);
    
    public List<ResponseBean> search (ApiForm input);
    
}











