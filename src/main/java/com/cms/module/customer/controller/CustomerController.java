package com.cms.module.customer.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.module.customer.entity.CustomerBean;
import com.cms.module.customer.entity.ResponsibleBean;
import com.cms.module.customer.form.CustomerForm;
import com.cms.module.customer.form.ResponsibleForm;
import com.cms.module.customer.service.CustomerService;

/**
 * 社員コントローラー
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService service;

    /**
     * 顧客情報検索
     *
     * @param input 入力データ
     * @return 顾客情報のリスト
     */
    @PostMapping("/searchCustomer")
    public Map<String, Object> searchContract(@RequestBody CustomerForm input) {
    	timeDelay();
        // 入力データをマップに変換
        Map<String, Object> conditions = new HashMap<>();
        if (input.getCustomer_name() != null) {
            conditions.put("customer_name", input.getCustomer_name());
        }
        if (input.getCustomer_type() != null) {
        	conditions.put("customer_type", input.getCustomer_type());
        }
        if (input.getCustomer_represent() != null) {
        	conditions.put("customer_represent", input.getCustomer_represent());
        }
        if (input.getCustomer_address() != null) {
        	conditions.put("customer_address", input.getCustomer_address());
        }
        conditions.put("currentPage", (input.getCurrentPage()-1)*input.getPageSize());
        conditions.put("pageSize", input.getPageSize());

        // サービスを呼び出して結果を取得
        return service.findAllCustomerWithPagination(conditions);
    }
    
    /**
     * 责任者情報検索
     *
     * @param input 入力データ
     * @return 责任者情報のリスト
     */
    @PostMapping("/searchResponsible")
    public Map<String, Object> searchResponsible(@RequestBody ResponsibleForm input) {
    	timeDelay();
        // 入力データをマップに変換
        Map<String, Object> conditions = new HashMap<>();
        if (input.getResponsible_name() != null) {
            conditions.put("responsible_name", input.getResponsible_name());
        }
        if (input.getCustomer_id() != 0) {
        	conditions.put("customer_id", input.getCustomer_id());
        }
        conditions.put("currentPage", (input.getCurrentPage()-1)*input.getPageSize());
        conditions.put("pageSize", input.getPageSize());

        // サービスを呼び出して結果を取得
        return service.findAllResponsibleWithPagination(conditions);
    }
    
    /**
     * 顾客信息初始化
     *
     * @param input customer_id
     * @return 顾客信息
     */
    @GetMapping("/customerDetail/{customer_id}")
    public CustomerBean getCustomerDetail(@PathVariable("customer_id") String customer_id) {
    	CustomerBean result = null;
        try {
        	result = service.findByCustomerId(customer_id);
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }
    
    /**
     * 责任者信息初始化
     *
     * @param input responsible_id
     * @return 责任者信息
     */
    @GetMapping("/responsibleDetail/{responsible_id}")
    public ResponsibleBean getResponsibleDetail(@PathVariable("responsible_id") String responsible_id) {
    	ResponsibleBean result = null;
        try {
        	result = service.findByResponsibleId(responsible_id);
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }
    
    /**
     * 获取营业组长所属员工ID
     *
     * @param input responsible_id
     * @return 责任者信息
     */
    @GetMapping("/departmentEmployee/{id}")
    public Map<String, Object> departmentEmployee(@PathVariable("id") String id) {
    	Map<String, Object> conditions = new HashMap<>();
    	conditions.put("employee", conditions);
        return conditions;
    }
    
    /**
     * 顧客情報削除
     *
     * @param input contract_id
     * @return message
     */
    @PostMapping("/deleteCustomer")
    public Map<String, Object> deleteCustomer(@RequestBody CustomerForm input){
        int id = input.getCustomer_id();
        LocalDateTime updated_at = input.getUpdated_at();
        String type = "customer";
        boolean check = positiveCheck(id,updated_at,type);
    	Map<String, Object> conditions = new HashMap<>();
    	conditions.put("type", type);
    	if(check) {
    	   if (id != 0) {
              conditions.put("customer_id", id);
           }
    	   return service.deleteSelected(conditions);    
    	}
    	else {
			conditions.put("message", "項目はすでに更新されました、再検索して直してください。");
			conditions.put("status", "error");
			return conditions;
    	}
    }
    
    /**
     * 責任者情報削除
     *
     * @param input contract_id
     * @return message
     */
    @PostMapping("/deleteResponsible")
    public Map<String, Object> deleteResponsible(@RequestBody ResponsibleForm input){
        int id = input.getResponsible_id();
        LocalDateTime updated_at = input.getUpdated_at();
        String type = "responsible";
        boolean check = positiveCheck(id,updated_at,type);
    	Map<String, Object> conditions = new HashMap<>();
    	conditions.put("type", type);
    	if(check) {
    	   if (id != 0) {
              conditions.put("responsible_id", id);
           }
    	   return service.deleteSelected(conditions);    
    	}
    	else {
			conditions.put("message", "項目はすでに更新されました、再検索して直してください。");
			conditions.put("status", "error");
			return conditions;
    	}
    }
    
    /**
     * 顧客情報登録
     *
     * @param input contract_id
     * @return message
     */
    @PostMapping("/registerCustomer")
    public Map<String, Object> registerCustomer(@RequestBody CustomerForm input){
    	Map<String, Object> response = new HashMap<>();
            try {
                input.setCreated_at(LocalDateTime.now());
                input.setUpdated_at(LocalDateTime.now());
                input.setDeleteFlag(false);
                int id = service.registerCustomer(input);
                response.put("customer_id",id);
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
     * 顧客情報登録
     *
     * @param input contract_id
     * @return message
     */
    @PostMapping("/registerResponsible")
    public Map<String, Object> registerResponsible(@RequestBody ResponsibleForm input){
    	Map<String, Object> response = new HashMap<>();
            try {
                input.setCreated_at(LocalDateTime.now());
                input.setUpdated_at(LocalDateTime.now());
                input.setDeleteFlag(false);
                service.registerResponsible(input);
                response.put("message", "登録が成功しました。");
                response.put("status", "success");
            } catch (Exception e) {
                response.put("message", "登録に失敗しました: " + e.getMessage());
                response.put("status", "error");
                System.out.println(e);
            } 
    	return response;
    }
    
    /**
     * 更新顾客信息
     *
     * @param input
     * @return message
     */
    @PostMapping("/updateCustomer")
    public Map<String, Object> updateCustomer(@RequestBody CustomerBean input) {
        int id = input.getCustomer_id();
        LocalDateTime updated_at = input.getUpdated_at();
        String type = "customer";
        boolean check = positiveCheck(id,updated_at,type);
        Map<String, Object> conditions = new HashMap<>();
        if(check) {
        	try {
        		     input.setUpdated_at(LocalDateTime.now());
	    	         boolean updated = service.updateCustomer(input);
	    	         if(updated) {
	    	            conditions.put("message", "登録が成功しました。");
	    	            conditions.put("status", "success");
	    	         }
	    	         else {
	    	                conditions.put("message", "登録に失敗しました、管理者と連絡してください。");
	    	                conditions.put("status", "error");
	    	         }
    	         }
        	catch(Exception e) {
                conditions.put("message", "登録に失敗しました: " + e.getMessage());
                conditions.put("status", "error");
                System.out.println(e);
        	}
    	}
    	else {
			conditions.put("message", "項目はすでに更新されました、再検索して直してください。");
			conditions.put("status", "error");
    	}
        return conditions;
    }
    
    /**
     * 更新顾客信息
     *
     * @param input
     * @return message
     */
    @PostMapping("/updateResponsible")
    public Map<String, Object> updateResponsible(@RequestBody ResponsibleBean input) {
        int id = input.getCustomer_id();
        LocalDateTime updated_at = input.getUpdated_at();
        String type = "responsible";
        boolean check = positiveCheck(id,updated_at,type);
        Map<String, Object> conditions = new HashMap<>();
        if(check) {
        	try {
        		     input.setUpdated_at(LocalDateTime.now());
	    	         boolean updated = service.updateResponsible(input);
	    	         if(updated) {
	    	            conditions.put("message", "登録が成功しました。");
	    	            conditions.put("status", "success");
	    	         }
	    	         else {
	    	                conditions.put("message", "登録に失敗しました、管理者と連絡してください。");
	    	                conditions.put("status", "error");
	    	         }
    	         }
        	catch(Exception e) {
                conditions.put("message", "登録に失敗しました: " + e.getMessage());
                conditions.put("status", "error");
                System.out.println(e);
        	}
    	}
    	else {
			conditions.put("message", "項目はすでに更新されました、再検索して直してください。");
			conditions.put("status", "error");
    	}
        return conditions;
    }
    
    /**
     * 楽観チェック
     *
     * @param contract_id
     * @return Boolean
     */
    private boolean positiveCheck(int id , LocalDateTime updated_at ,String type) {
    	boolean check = true;
    	LocalDateTime update_now = service.getUpdate(id,type);
    	if(update_now.isBefore(updated_at) || update_now.isEqual(updated_at)){
    		check = true;
    	}
    	else {
    		check = false;
    	}
    	return check;
    }
    
    //模拟读取时间
    private void timeDelay () {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}









