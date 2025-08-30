package com.cms.module.regist.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.module.employee.entity.Employees;
import com.cms.module.regist.mapper.RegistMapper;

/**
 * Employee のサービスクラス
 */
@Service
public class RegistServiceImpl implements RegistService {

	/**複数テーブル処理*/
	@Autowired
	RegistMapper mapper;
	
	@Override
	public Employees getLoginInfo(String user) {

        return mapper.getPwd(user);
    }
	
	 public Map<String, Object> updateLoginInfo(Map<String, Object> conditions) {
	        Map<String, Object> result = new HashMap<>();
	        try {
	        	mapper.updateLoginInfo(conditions);
	        	result.put("success",true);
	        	result.put("message", "");
	        }catch(Exception e) {
	        	result.put("success", false);
	        	result.put("message", e);
	        }
	        return result;

	    }
}
