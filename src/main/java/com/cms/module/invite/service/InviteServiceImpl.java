package com.cms.module.invite.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.module.invite.mapper.InviteMapper;

/**
 * Employee のサービスクラス
 */
@Service
public class InviteServiceImpl implements InviteService {

	/**複数テーブル処理*/
	@Autowired
	InviteMapper mapper;


	@Override
	public int registData(Map<String, Object> BasicInfo) {
		return mapper.insert(BasicInfo);
	}



}
