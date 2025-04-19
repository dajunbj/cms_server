package com.cms.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cms.api.entity.ApiBean;
import com.cms.api.entity.ResponseBean;
import com.cms.api.form.ApiForm;

/**
 * ユーザー情報 Mapper
 */
@Mapper
public interface ApiMapper {
	
    /**
     * 根据契约ID查找初始化信息
     * 
     * @param 契约ID
     * @return 契约情报
     */
	ApiBean shareCheck (int id);
	
	public List<ResponseBean> search (ApiForm input);
	
}









