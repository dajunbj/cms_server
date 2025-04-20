package com.cms.module.attendance.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cms.base.controller.BaseController;
import com.cms.base.controller.ResultUtils;
import com.cms.module.attendance.entity.Attendance;
import com.cms.module.attendance.form.AttendanceForm;
import com.cms.module.attendance.service.AttendanceService;
import com.cms.module.employee.entity.Employees;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/attendance")
public class AttendanceController extends BaseController {

    @Autowired
    AttendanceService service;

    //----------一覧画面----------
    @GetMapping("/listview/search")
    public ResponseEntity<?> getAttendanceList(
            @RequestParam String month,
            @RequestParam String employeeName, HttpSession session) {
    	
    	//セッションからログイン情報を取得する
    	Employees loginInfo = getLoginInfo(session, "userinfo", Employees.class);
    	
    	Map<String, Object> conditions = new HashMap<String, Object>();
    	conditions.put("employee_id", loginInfo.getEmployee_id());
    	
//    	if(StringUtils.isNotEmpty(month)) {
//        	conditions.put("month", month);
//    	}
//    	if(StringUtils.isNotEmpty(employeeName)) {
//    		conditions.put("employeeName", employeeName);
//    	}
    	List<Attendance> result = service.searchAllData(conditions);
    	
        return result.isEmpty()
                ? ResultUtils.error("検索結果がありません")
                : ResultUtils.success(result, result.size());
    }
    
    
//    @GetMapping("/listview/init")
//    public AttendanceForm getCurrentMonthAttendance(
//            @RequestParam String employeeId,
//            @RequestParam String month) {
//        return service.getAttendanceForInit(employeeId, month);
//    }
    
    
    //----------登録画面----------
	  @PostMapping("/registerview/registInit")
	  public ResponseEntity<?> registerAttendanceInit( HttpSession session) {
	      AttendanceForm form = new AttendanceForm();

	      Employees info = getLoginInfo(session, "userinfo", Employees.class);
	      
	      //社員情報設定
	      form.setEmployee_id(info.getEmployee_id());
	      form.setEmployeeName(info.getName());

	      //勤怠検索
	      Map<String,Object> param = new HashMap<String,Object>();
	      List<Attendance> result = service.getMonthlyData(param);
	      if (result != null && result.size() > 0) {
	          //社員の勤怠情報がある場合
              form.setAttendanceList(result);
	      }
	      
	      return ResultUtils.success(form);
	  }
    
//    @PostMapping("/registerview/regist")
//    public ResponseEntity<?> registerAttendance(@RequestBody AttendanceForm form) {
//        List<Attendance> list = form.getAttendanceList();
//        service.updateModified(list);
//        return ResponseEntity.ok().build();
//    }
    
    //----------更新画面----------
//    @PutMapping("/editview/edit/{id}")
//    public ResponseEntity<?> updateAttendance(@PathVariable Long id, @RequestBody AttendanceForm dto) {
//    	service.updateAttendance(id, dto);
//        return ResponseEntity.ok().build();
//    }
    
    //----------共通メソッド----------
    /**
     * 勤怠情報取得（社員の月別） 
     * 
     * @param employee_id 社員ID
     * @param contract_id 
     * @param month       年月
     * @return 月次勤務データ
     */
    @GetMapping("/getMonthly")
    public List<Attendance> getMonthly(@RequestParam Integer employee_id,
                                             @RequestParam Integer contract_id,
                                             @RequestParam String month) {
    
        Map<String, Object> params = new HashMap<>();
//        params.put("employee_id", employee_id);
//        params.put("contract_id", contract_id);
//        params.put("month", month);
        
        return service.getMonthlyData(params);
    }
}