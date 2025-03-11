package com.cms.module.popup.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.module.contract.form.CaseForm;
import com.cms.module.contract.form.ContractForm;
import com.cms.module.popup.form.DepartmentForm;
import com.cms.module.popup.service.PopUpService;

/**
 * 社員コントローラー
 */
@RestController
@RequestMapping("/popup")
public class PopUpController {

    @Autowired
    PopUpService service;

    /**
     * 案件検察
     *
     * @param input 入力データ
     * @return 案件情報
     */
    @PostMapping("/caseSearch")
    public Map<String, Object> searchCase(@RequestBody CaseForm input) {
    	
        // 入力データをマップに変換
        Map<String, Object> conditions = new HashMap<>();
        
        if (input.getCase_title() != null) {
            conditions.put("name", input.getCase_title());
        }
        // サービスを呼び出して結果を取得
        return service.findCase(conditions);
    }
    
    /**
     * 社员検察
     *
     * @param input 入力データ
     * @return 社员情報
     */
    @PostMapping("/employeeSearch")
    public Map<String, Object> searchEmployee(@RequestBody ContractForm input) {
    	
        // 入力データをマップに変換
        Map<String, Object> conditions = new HashMap<>();
        
        if (input.getName() != null) {
            conditions.put("name", input.getName());
        }
        // サービスを呼び出して結果を取得
        return service.findEmployee(conditions);
    }
    
    /**
     * 顾客検察popup
     *
     * @param 顾客姓名
     * @return 社员情報
     */
    @PostMapping("/customerSearch")
    public Map<String, Object> customerSearch(@RequestBody ContractForm input) {
    	
        // 入力データをマップに変換
        Map<String, Object> conditions = new HashMap<>();
        
        if (input.getName() != null) {
            conditions.put("customer_name", input.getName());
        }
        // サービスを呼び出して結果を取得
        return service.findCustomerByName(conditions);
    }
    
    /**
     * 顾客検察popup
     *
     * @param 顾客姓名
     * @return 社员情報
     */
    @PostMapping("/departmentSearch")
    public Map<String, Object> departmentSearch(@RequestBody DepartmentForm input) {
    	
        // 入力データをマップに変換
        Map<String, Object> conditions = new HashMap<>();
        
        if (input.getDepartment_name() != null) {
            conditions.put("customer_name", input.getDepartment_name());
        }
        // サービスを呼び出して結果を取得
        return service.findDepartmentByName(conditions);
    }
    
}







