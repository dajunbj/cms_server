package com.cms.module.attendance.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.module.attendance.entity.Attendance;
import com.cms.module.attendance.mapper.AttendanceMapper;

@Service
public class AttendanceServiceImpl implements AttendanceService {

	@Autowired
	private AttendanceMapper mapper;

	@Override
	public int selectCouint(Map<String, Object> conditions) {
		mapper.select(conditions);
		return 0;
	}

	/**
	 * 一覧検索
	 */
	@Override
	public List<Attendance> searchAllData(Map<String, Object> conditions) {
		
		return mapper.select(conditions);
	}

	@Override
	public void registData(Attendance obj) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public Attendance searchSingleById(String id) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void initEdit(String id) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void updateData(Attendance obj) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void initDetail(String id) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void deleteDataById(Attendance obj) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void deleteAll(List<Integer> ids) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}