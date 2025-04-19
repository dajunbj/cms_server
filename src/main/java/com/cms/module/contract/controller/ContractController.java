package com.cms.module.contract.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.module.contract.entity.ContractBean;
import com.cms.module.contract.form.CaseForm;
import com.cms.module.contract.form.ContractForm;
import com.cms.module.contract.service.ContractService;

/**
 * 社員コントローラー
 */
@RestController
@RequestMapping("/contract")
public class ContractController {

    @Autowired
    ContractService service;

    /**
     * 契約情報検索
     *
     * @param input 入力データ
     * @return 社員情報のリスト
     */
    @PostMapping("/search")
    public Map<String, Object> searchContract(@RequestBody ContractForm input) {
    	
        // 入力データをマップに変換
        Map<String, Object> conditions = new HashMap<>();
        
        if (input.getName() != null) {
            conditions.put("name", input.getName());
        }
        if (input.getCustomer_name() !=null) {
        	conditions.put("customer_name", input.getCustomer_name());
        }
        if (input.getStart_date() !=null) {
        	conditions.put("start_date", input.getStart_date());
        }
        if (input.getEnd_date() !=null) {
        	conditions.put("end_date", input.getEnd_date());
        }
        if (input.getEmployee_type().equals("1")) {
        	conditions.put("employee_type", "BP");
        }
        else if (input.getEmployee_type().equals("2")) {
        	conditions.put("employee_type", "正社員");
        }
        else {
        	conditions.put("employee_type", null);
        }
        if (input.getResponsible_name() != null) {
            conditions.put("responsible_name", input.getResponsible_name());
        }
        conditions.put("currentPage", (input.getCurrentPage()-1)*input.getPageSize());
        conditions.put("pageSize", input.getPageSize());

        // サービスを呼び出して結果を取得
        return service.findAllWithPagination(conditions);
    }

    /**
     * 契約先に終了
     *
     * @param input 入力データ
     * @return null
     */
    @PostMapping("/finishManual")
    public Map<String, Object> finishMuanal(@RequestBody List<ContractForm> input) {
    	delayTime();
    	Map<String, Object> response = new HashMap<>();
    	boolean hasFault = false;
    	for(int i = 0;i< input.size();i++) {
    		Date endDate = service.getEndDate(input.get(i));
    		Date hopeDate = input.get(i).getEnd_date();
    		LocalDate nowDate = LocalDate.now();
    		LocalDate inputLocalDate = hopeDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
    		if(nowDate.isAfter(inputLocalDate)) {
    			hasFault = true;
    			response.put("message","日期不正の条目があります。");
    			response.put("status", "error");
    			break;
    		}
    		if(hopeDate.compareTo(endDate) > 0) {
    			hasFault = true;
        		response.put("message","日期不正の条目があります。");
        		response.put("status", "error");
        		break;
    		}
    	}
    	if(!hasFault) {
    		for(int i = 0;i< input.size();i++) {
    			try {
    			     service.finishManual(input.get(i));
    			     }
    			catch(Exception e){
    				 response.put("message", "登録失敗しました：" + e.getMessage());
    	             response.put("status", "error");
    	             return response;
    			}
    			
    		}
		     response.put("message","登録成功しました。");
		     response.put("status", "success");
    	}
    	return response;
    }
    
    /**
     * 契約延長
     *
     * @param input 入力データ
     * @return null
     */
    @PostMapping("/extendManual")
    public Map<String, Object> extendManual(@RequestBody List<ContractForm> input){
    	delayTime();
    	Map<String, Object> response = new HashMap<>();
    	boolean hasFault = false;
    	for(int i = 0;i< input.size();i++) {
    		Date endDate = service.getEndDate(input.get(i));
    		Date hopeDate = input.get(i).getEnd_date();
    		LocalDate nowDate = LocalDate.now();
    		LocalDate inputLocalDate = hopeDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
    		if(nowDate.isAfter(inputLocalDate)) {
    			hasFault = true;
    			response.put("message","日期不正の条目があります。");
    			response.put("status", "error");
    			break;
    		}
    		if(hopeDate.compareTo(endDate) < 0) {
    			hasFault = true;
        		response.put("message","日期不正の条目があります。");
        		response.put("status", "error");
        		break;
    		 }
    	}
    	    if(!hasFault) {
    		for(int i = 0;i< input.size();i++) {
    			try {
    			     service.finishManual(input.get(i));
    			     }
    			catch(Exception e){
    				 response.put("message", "登録失敗しました：" + e.getMessage());
    	             response.put("status", "error");
    	             return response;
    			}
    			
    		}
		     response.put("message","登録成功しました。");
		     response.put("status", "success");
    	}
    	return response;
    }
    
    /**
     * 契約登録
     *
     * @param 契约情报
     * @return 結果メッセージ
     */
    @PostMapping("/register")
    public Map<String, Object> registerContract(@RequestBody ContractBean input) {
    	delayTime();
        Map<String, Object> response = new HashMap<>();
        try {
            input.setUpdate_time(LocalDateTime.now());
            service.saveContract(input);
            response.put("message", "登録が成功しました");
            response.put("status", "success");
        } catch (Exception e) {
            response.put("message", "登録に失敗しました: " + e.getMessage());
            response.put("status", "error");
            System.out.println(e);
        }
        return response;
    }
    
    /**
     * 契約更新
     *
     * @param input 入力データ
     * @return 更新結果
     */
    @PutMapping("/update")
    public Map<String, Object> updateContract(@RequestBody ContractBean input) {
    	delayTime();
        int contract_id = input.getContract_id();
    	LocalDateTime update_origin = service.getUpdateTime(contract_id);
    	LocalDateTime update_compare = input.getUpdate_time();
        Map<String, Object> response = new HashMap<>();
        try {
		        if(update_origin.isBefore(update_compare)||update_origin.isEqual(update_compare)) {
		        	boolean updated = service.updateContract(input);
		        	if(updated) {
		        		response.put("message", "登録が成功しました");
		                response.put("status", "success");
		        	}
		        	else {
		                response.put("message", "登録に失敗しました");
		                response.put("status", "error");
		        	}
		        }
		        else {
		            response.put("message", "項目はすでに更新されました、再検索して直してください。");
		            response.put("status", "updated");
		        }
	        }catch(Exception e) {
	    		response.put("message", "削除失敗しました: " + e.getMessage());
	            response.put("status", "error");
	        }
        return response;
    }
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
     * 顧客検察
     *
     * @param input 入力データ
     * @return 顧客情報
     */
    @PostMapping("/searchCustomer")
    public Map<String, Object> searchCustomer(@RequestBody ContractForm input){
    	Map<String, Object> conditions = new HashMap<>();
    	if (input.getCustom_id() != 0) {
            conditions.put("custom_id", input.getCustom_id());
        }
    	return service.findCustomer(conditions);    
    }
    
    /**
     * 契約削除
     *
     * @param input contract_id
     * @return message
     */
    @PostMapping("/deleteSelected")
    public Map<String, Object> deleteSelected(@RequestBody ContractForm input){
        int contract_id = input.getContract_id();
    	LocalDateTime update_origin = service.getUpdateTime(contract_id);
    	LocalDateTime update_compare = input.getUpdate_time();
    	Map<String, Object> conditions = new HashMap<>();
    	try{
    		if(update_origin.isBefore(update_compare)||update_origin.isEqual(update_compare)) {
    			if (input.getContract_id() != 0) {
    				conditions.put("contract_id", input.getContract_id());
    			}
    			return service.deleteSelected(conditions);    
    			}
    		else {
    			conditions.put("message", "項目はすでに更新されました、再検索して直してください。");
    			conditions.put("status", "error");
    			}
    	}catch(Exception e) {
    		conditions.put("message", "削除失敗しました: " + e.getMessage());
            conditions.put("status", "error");
    	}
    	return conditions;
    }
    
    /**
     * 契約信息初始化
     *
     * @param input contract_id
     * @return 契约信息
     */
    @GetMapping("/contractDetail/{contract_id}")
    public ContractBean getContractDetail(@PathVariable("contract_id") String contract_id) {
    	ContractBean result = null;
        try {
        	result = service.findByContractId(contract_id);
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }
    
    private void delayTime() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
}
