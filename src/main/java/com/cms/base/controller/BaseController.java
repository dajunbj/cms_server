package com.cms.base.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 親クラス
 */
public class BaseController extends SessionBase {

	private boolean STATUS_SUCCESS = true;
	private boolean STATUS_FAILURE = false;
	
	/**処理ステータス*/
	private String RESPONSE_STATUS = "status";
	/**処理戻り値*/
	private String RESPONSE_DATA = "data";
	/**検索結果件数*/
	private String RESPONSE_COUNT = "total";
	/**メッセージ*/
	private String RESPONSE_MESSAGE = "message";
	
    /**
     * 成功レスポンス（メッセージ付き）
     */
    protected Map<String, Object> success(Object data) {
    	
        Map<String, Object> response = new HashMap<>();
        response.put(RESPONSE_STATUS, STATUS_SUCCESS);
        response.put(RESPONSE_DATA, data);

        return response;
    }

    /**
     * 成功レスポンス（List専用、メッセージ付き）
     */
    protected Map<String, Object> success(List<?> data, int totalCount) {
    	
        Map<String, Object> response = new HashMap<>();
        
        response.put(RESPONSE_STATUS, STATUS_SUCCESS);
        response.put(RESPONSE_DATA, data);
        response.put(RESPONSE_COUNT, totalCount);
  
        return response;
    }
    
    /**
     * 成功レスポンス（メッセージ付き）
     */
    protected Map<String, Object> success(String message) {
    	
        Map<String, Object> response = new HashMap<>();
        
        response.put(RESPONSE_STATUS,STATUS_SUCCESS);
        response.put(RESPONSE_MESSAGE, message);
        
        return response;
    }
    
    /**
     * エラーレスポンス
     */
    protected Map<String, Object> error(String message) {
    	
        Map<String, Object> response = new HashMap<>();
        
        response.put(RESPONSE_STATUS, STATUS_FAILURE);
        response.put(RESPONSE_MESSAGE, message);
        
        return response;
    }
}