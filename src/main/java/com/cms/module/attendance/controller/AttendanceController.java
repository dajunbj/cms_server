package com.cms.module.attendance.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    @PostMapping("/listview/search")
    public ResponseEntity<?> getAttendanceList(
    		@RequestBody Map<String, Object> input, HttpSession session) {
    	
    	//セッションからログイン情報を取得する
    	Employees loginInfo = getLoginInfo(session, "userinfo", Employees.class);

    	List<Attendance> result = service.getMonthlyData(input);
    	
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
    @GetMapping("/checkRegistered")
    public Map<String, Object> checkAlreadyRegistered(
            @RequestParam String month,
            HttpSession session) {

        Employees loginUser = getLoginInfo(session, "userinfo", Employees.class);
        if (loginUser == null) {
            return Map.of("exists", false); // fallback
        }

        Map<String, Object> param = new HashMap<>();
        param.put("employee_id", loginUser.getEmployee_id());
        param.put("month", month);

        List<Attendance> existing = service.getMonthlyData(param);
        boolean exists = existing != null && !existing.isEmpty();

        return Map.of("exists", exists);
    }

    
      /**
       *登録画面初期化
       *
       *@param month 勤務付月
       *@param session
       */
      @PostMapping("/registerview/registInit")
	  public ResponseEntity<?> registerAttendanceInit(@RequestBody Map<String, Object> input, HttpSession session) {
		  
	      AttendanceForm form = new AttendanceForm();

	      //ログイン情報取得
	      Employees info = getLoginInfo(session, "userinfo", Employees.class);
	      
	      // 月パラメータ取得（空なら現在月に設定）
	      String month = input.get("month") != null ? input.get("month").toString() : "";
          // システムの現在年月取得（yyyy-MM）
          java.time.LocalDate now = java.time.LocalDate.now();
          month = now.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM"));
	      //引数検索
	      Map<String,Object> param = new HashMap<String,Object>();
	      param.put("employee_id", info.getEmployee_id());
	      param.put("month", month);
	      
	      List<Attendance> result = service.getMonthDataByEmployeeId(param);
	      
	      if (result != null && result.size() > 0) {
	          //社員の勤怠情報がある場合
              form.setAttendanceList(result);
	      } else {
	    	  
	      }
	      
	      return ResultUtils.success(result);
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