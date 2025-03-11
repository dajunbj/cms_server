package com.cms.api.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.api.entity.ResponseBean;
import com.cms.api.form.ApiForm;
import com.cms.api.service.ApiService;

/**
 * 社員コントローラー
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    ApiService service;
    
    /**
     * 检索权限Check
     *
     * @param input 共享会社id
     * @return 检查结果代码
     */
    public int shareCheck(int input) {
    	int message = service.shareCheck(input);
        return message;
    }
    
    /**
     * 共享案件情报検索
     *
     * @param input 入力データ
     * @return 共享情报
     */
    @PostMapping("/search")
    public Map<String,Object> mainWay (@RequestBody ApiForm input) {
    	
        // 入力データをマップに変換
        Map<String,Object> results = new LinkedHashMap<>();
        int check = shareCheck(input.getShare_company_id());
        List<ResponseBean> bean = new ArrayList<>();
        if(check == 1) {
        	results.put("errCd:","err004");
        	results.put("errMessage:","指定会社は存在しない、ご確認してください。");
        	results.put("details", "指定会社はデータベースに存在しません。");
        	return results;
        }
        else if (check == 2) {
        	results.put("errCd:","err005");
        	results.put("errMessage:","指定会社は権限がない、管理者と連絡してください。");
        	results.put("details", "検索者は検索の権限がありません。");
        	return results;
        }
        else {
        	bean = service.search(input);
        }
        results.put("total", bean.size());
        if (!bean.isEmpty()) {
            ResponseBean firstBean = bean.get(0);
            results.put("companyName", firstBean.getCompany_name());
            results.put("responsiblePerson", firstBean.getName());
            results.put("contactPhone", firstBean.getPhone_number());
        }
        List<Map<String, Object>> data = new ArrayList<>();

        for (ResponseBean beanB : bean) {
            Map<String, Object> sourceInfoMap = new LinkedHashMap<>();
            sourceInfoMap.put("clientName", beanB.getCustomer_name());
            sourceInfoMap.put("responsiblePerson", beanB.getResponsible_name());
            sourceInfoMap.put("responsibleContactPhone", beanB.getResponsible_phone());
        	
            Map<String, Object> projectMap = new LinkedHashMap<>();
            projectMap.put("projectId", beanB.getCase_id());
            projectMap.put("title", beanB.getCase_title());
            projectMap.put("description", beanB.getCase_description());
            projectMap.put("budget", beanB.getCase_budget());
            projectMap.put("startMonth", beanB.getStart_date().toString()); 
            projectMap.put("technicalRequirements", beanB.getCase_skill());
            projectMap.put("location", beanB.getCase_address());

            sourceInfoMap.put("project", projectMap); 

            Map<String, Object> dataEntry = new LinkedHashMap<>();
            dataEntry.put("sourceInfo", sourceInfoMap);

            data.add(dataEntry);
        }
        
        results.put("data", data);
        
        return results;
        
    }
}
