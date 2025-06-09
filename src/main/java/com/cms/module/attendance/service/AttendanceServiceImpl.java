package com.cms.module.attendance.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
		
		return mapper.getMonthlyData(conditions);
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

	/**勤務一覧画面検索
	 *
	 * @param conditions 処理条件
	 * @return 勤務データ
	 */
	@Override
	public List<Attendance> getMonthlyData(Map<String, Object> conditions) {
		
		return mapper.getMonthlyData(conditions);
	}

	/**勤務登録画面初期化
	 *
	 * @param conditions 処理条件
	 * @return 勤務データ
	 */
	@Override
	public List<Attendance> getMonthDataByEmployeeId(Map<String, Object> conditions) {
		List<Attendance> ret = mapper.getMonthlyDataByEmployeeId(conditions);
		if (ret != null && ret.size() > 0) {
			return ret;
		} else {
			return generateData(conditions);
		}
	}
	/**
	 * 勤務データ作成
	 *
	 * @param conditions 処理条件
	 * @return 勤務データ
	 */
	/**
	 * 勤務データ作成
	 *
	 * @param conditions 処理条件
	 * @return 勤務データ
	 */
	private List<Attendance> generateData(Map<String, Object> conditions) {
	    Integer employeeId = (Integer) conditions.get("employee_id");
	    String month = (String) conditions.get("month"); // 例: "2025-05"

	    List<Attendance> generated = new ArrayList<>();
	    DateTimeFormatter ymFormatter = DateTimeFormatter.ofPattern("yyyy-MM");

	    YearMonth yearMonth = YearMonth.parse(month, ymFormatter);
	    int lengthOfMonth = yearMonth.lengthOfMonth();

	    for (int i = 1; i <= lengthOfMonth; i++) {
	        LocalDate current = yearMonth.atDay(i);
	        Attendance att = new Attendance();
	        att.setEmployee_id(employeeId);
	        att.setWorkday(current.toString()); // yyyy-MM-dd

	        if (isWeekend(current)) {
	            att.setAttendance_type("休日");
	        } else {
	            att.setAttendance_type("平日");
	            att.setStart_time("09:00");
	            att.setEnd_time("18:00");
	        }

	        att.setBreak_hours(1.0);
	        att.setIs_modified(false);
	        generated.add(att);
	    }

	    return generated;
	}

	
	/**
	 * 週末判定
	 *
	 * @param date 対象日付
	 * @return boolean 判定結果
	 */
	private boolean isWeekend(LocalDate date) {
	    DayOfWeek day = date.getDayOfWeek();
	    return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
	}
}