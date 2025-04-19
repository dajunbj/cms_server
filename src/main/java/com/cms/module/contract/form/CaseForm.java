package com.cms.module.contract.form;

import java.sql.Date;

import lombok.Data;

/**
 * 社員エンティティクラス
 */
@Data
public class CaseForm {

    /**
     * 案件ID
     */
    private int case_id;
    
    /**
     * 顧客ID
     */
    private int customer_id;
    
    /**
     * 案件のタイトル
     */
    private String case_title;
    
    /**
     * 案件の詳細
     */
    private String case_description;
    
    /**
     * 案件開始日
     */
    private Date start_date;
    
    /**
     * 案件終了日
     */
    private Date end_date;
    
    /**
     * 案件ステータス
     */
    private String case_status;

    /**
     * 作成日時
     */
    private Date created_at;
    
    /**
     * 更新日時
     */
    private Date updated_at;
    
}
