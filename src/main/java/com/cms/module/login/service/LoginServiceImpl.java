package com.cms.module.login.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.module.employee.entity.Employees;
import com.cms.module.login.mapper.LoginMapper;

/**
 * Employee のサービスクラス
 */
@Service
public class LoginServiceImpl implements LoginService {

	/**複数テーブル処理*/
	@Autowired
	LoginMapper mapper;
	
	@Override
	public Employees getLoginInfo(Map<String, Object> conditions) {

        return mapper.selectOne(conditions);
    }
}
