package com.cms.module.employee.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.module.employee.entity.Employee;
import com.cms.module.employee.form.EmployeeForm;
import com.cms.module.employee.service.EmployeeService;

/**
 * 社員コントローラー
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeService service;

    /**
     * 社員情報検索
     *
     * @param input 入力データ
     * @return 社員情報のリスト
     */
    @PostMapping("/search")
    public Map<String, Object> searchEmployees(@RequestBody EmployeeForm input) {
    	
        // 入力データをマップに変換
        Map<String, Object> conditions = new HashMap<>();
        
        if (input.getEmployeeName() != null) {
            conditions.put("name", input.getEmployeeName());
        }

        conditions.put("currentPage", input.getCurrentPage()-1);
        conditions.put("pageSize", input.getPageSize());

        // サービスを呼び出して結果を取得
        return service.findAllWithPagination(conditions);
        
    }

    /**
     * 社員登録
     *
     * @param employee 社員情報
     * @return 結果メッセージ
     */
    @PostMapping("/register")
    public Map<String, Object> registerEmployee(@RequestBody Employee employee) {
        Map<String, Object> response = new HashMap<>();
        try {
            employee.setRegistDay(LocalDateTime.now());
            employee.setUpdateDay(LocalDateTime.now());
            service.saveEmployee(employee);
            response.put("message", "登録が成功しました");
            response.put("status", "success");
        } catch (Exception e) {
            response.put("message", "登録に失敗しました: " + e.getMessage());
            response.put("status", "error");
            System.out.println(e);
        }
        return response;
    }
    
    @GetMapping("/detail/{id}")
    public Employee getEmployeeDetail(@PathVariable("id") String id) {
    	Employee result = null;
        try {
        	result = service.findById(id);
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }

    @PutMapping("/update")
    public String updateEmployee(@RequestBody Employee employee) {
        boolean updated = service.updateEmployee(employee);
        return updated ? "更新成功" : "更新失敗";
    }
    
    
//    /**
//     * 社員一括削除
//     *
//     * @param request 削除リクエスト
//     * @return 残りの社員情報リスト
//     */
//    @PostMapping("/deleteAll")
//    public List<Employee> deleteAll(@RequestBody DeleteRequest request) {
//        // 削除対象のIDリストを取得
//        List<String> delListId = request.getDelListId();
//        System.out.println("削除するIDリスト: " + delListId);
//
//        // サービスで削除処理を実行
//        delListId.forEach(id -> service.deleteById(Long.parseLong(id)));
//
//        // 削除後の社員リストを取得
//        return service.findAll();
//    }
}
