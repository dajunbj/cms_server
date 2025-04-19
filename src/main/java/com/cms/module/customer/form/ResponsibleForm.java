package com.cms.module.customer.form;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * 社員エンティティクラス
 */
@Data
public class ResponsibleForm {

    /**
     * 責任者のユニーク識別子
     */
    private int responsible_id;
    
    /**
     * 対応する顧客のID
     */
    private int customer_id;
    
    /**
     * 責任者所属部門ID
     */
    private int department_id;
    
    /**
     * 責任者所属部門ID
     */
    private int employee_id;
    
    /**
     * 責任者名
     */
    private String responsible_name;
    
    /**
     * 責任者のメールアドレス
     */
    private String responsible_email;
    
    /**
     * 責任者の電話番号
     */
    private String responsible_phone;
    
    /**
     * 責任者の役割
     */
    private String responsible_type;

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
