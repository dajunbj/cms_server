package com.cms.module.customer.form;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * 社員エンティティクラス
 */
@Data
public class CustomerForm {

    /**
     * 顧客ID
     */
    private int customer_id;
    
    /**
     * 顧客名
     */
    private String customer_name;
    
    /**
     * 顧客Email
     */
    private String customer_email;
    
    /**
     * 顧客电话号码
     */
    private String customer_phone;
    
    /**
     * 顧客タイプ
     */
    private String customer_type;
    
    /**
     * 顧客代表者名
     */
    private String customer_represent;
    
    /**
     * 顧客住所
     */
    private String customer_address;
    
    /**
     * 顧客FAX
     */
    private String customer_fax;

    /**
     * 顧客郵便番号
     */
    private String customer_mail;
    
    /**
     * 削除マーク
     */
    private Boolean deleteFlag;
    
    /**
     * 作成日時
     */
    private LocalDateTime created_at;
    
    /**
     * 更新日時
     */
    private LocalDateTime updated_at;
    
    /**
     * 当前页码
     */
    private int currentPage;
    /**
     * 每页数量
     */
    private int pageSize;
    
}
