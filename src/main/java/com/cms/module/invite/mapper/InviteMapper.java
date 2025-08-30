package com.cms.module.invite.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * ユーザー情報 Mapper
 */
@Mapper
public interface InviteMapper {
	int insert(Map<String, Object> BasicInfo);
}