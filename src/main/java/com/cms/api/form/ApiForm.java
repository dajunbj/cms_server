package com.cms.api.form;

import java.sql.Date;

import lombok.Data;

/**
 * 社員エンティティクラス
 */
@Data
public class ApiForm {

    /**
     * 共有会社ID
     */
    private int share_company_id;
    
    /**
     * 案件開始日
     */
    private Date start_date;
    
    /**
     * 案件終了日
     */
    private Date end_date;
    
    /**
     * 案件の技術要件
     */
    private String case_skill;

    /**
     * 案件の作業場所
     */
    private String case_address;
}
