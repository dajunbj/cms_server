package com.cms.module.employee.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.base.controller.ResultUtils;
import com.cms.common.util.StringUtils;
import com.cms.module.employee.entity.Employees;
import com.cms.module.employee.form.EmployeeForm;
import com.cms.module.employee.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeService service;

    @PostMapping("/search")
    public ResponseEntity<?> searchEmployees(@RequestBody EmployeeForm input) {
        Map<String, Object> conditions = new HashMap<>();
        if (StringUtils.isNotEmpty(input.getEmployeeName())) {
            conditions.put("name", input.getEmployeeName());
        }
        conditions.put("pageSize", input.getPageSize());
        conditions.put("offsetVal", (input.getCurrentPage() - 1) * input.getPageSize());

        List<Employees> result = service.searchAllData(conditions);
        int total = service.selectCouint(conditions);

        return result.isEmpty()
                ? ResultUtils.error("検索結果がありません")
                : ResultUtils.success(result, total);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Employees employee) {
        try {
            service.registData(employee);
            return ResultUtils.success("登録成功");
        } catch (Exception e) {
            return ResultUtils.serverError("登録失敗: " + e.getMessage());
        }
    }

    @PostMapping("/deleteAll")
    public ResponseEntity<?> deleteAll(@RequestBody List<Integer> ids) {
        try {
            service.deleteAll(ids);
            return ResultUtils.success("削除成功");
        } catch (Exception e) {
            return ResultUtils.serverError("削除失敗: " + e.getMessage());
        }
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> detail(@PathVariable String id) {
        Employees e = service.searchSingleById(id);
        return e != null ? ResultUtils.success(e) : ResultUtils.error("社員情報が見つかりません");
    }
    
    @GetMapping("/initEdit/{id}")
    public ResponseEntity<?> initEdit(@PathVariable String id) {
        Employees e = service.searchSingleById(id);
        return e != null ? ResultUtils.success(e) : ResultUtils.error("社員情報が見つかりません");
    }
    
    @GetMapping("/edit/{id}")
    public ResponseEntity<?> edit(@PathVariable String id) {
        Employees e = service.searchSingleById(id);
        return e != null ? ResultUtils.success(e) : ResultUtils.error("社員情報が見つかりません");
    }
}
