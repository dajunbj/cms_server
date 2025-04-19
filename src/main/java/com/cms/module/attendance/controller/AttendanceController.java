package com.cms.module.attendance.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cms.base.controller.ResultUtils;
import com.cms.module.attendance.entity.Attendance;
import com.cms.module.attendance.service.AttendanceService;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    AttendanceService service;

    //----------一覧画面----------
    @GetMapping("/listview/search")
    public ResponseEntity<?> getAttendanceList(
            @RequestParam String month,
            @RequestParam String employeeName) {
    	
    	Map<String, Object> conditions = new HashMap<String, Object>();
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
//    @GetMapping("/getMonthly")
//    public List<Attendance> getMonthly(@RequestParam Integer employee_id,
//                                             @RequestParam Integer contract_id,
//                                             @RequestParam String month) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("employee_id", employee_id);
//        params.put("contract_id", contract_id);
//        params.put("month", month);
//        return service.getOrInitMonthlyData(params);
//    }
}