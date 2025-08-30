package com.cms.module.print.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.module.print.mapper.printMapper;

/**
 * Employee のサービスクラス
 */
@Service
public class printServiceImpl implements printService {

	/**複数テーブル処理*/
	@Autowired
	printMapper mapper;
	
	@Override
	public List<Map<String, ?>> printInfo(){
		List<Map<String, ?>> result = mapper.printInfo();
		return result;
	}

}
