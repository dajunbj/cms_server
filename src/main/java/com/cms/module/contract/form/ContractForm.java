package com.cms.module.contract.form;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;

/**
 * 社員エンティティクラス
 */
@Data
public class ContractForm {

    /**
     * 契約ID
     */
    private int contract_id;
    
    /**
     * 顧客ID
     */
    private int custom_id;
    
    /**
     * 社員ID
     */
    private int employee_id;
    
    /**
     * 責任者ID
     */
    private int responsible_id;
    
    /**
     * 案件ID
     */
    private int case_id;
    
    /**
     * 社員名前
     */
    private String name;
    
    /**
     * 顧客名前
     */
    private String customer_name;
    
    /**
     * 責任者名前
     */
    private String responsible_name;

    /**
     * 社員タイプ
     */
    private String employee_type;
    
    /**
     * 契約開始日
     */
    private Date start_date;
    
    /**
     * 契約終了日
     */
    private Date end_date;
    
    /**
     * 単価
     */
    private BigDecimal unit_price;
    
    /**
     * 税率
     */
    private BigDecimal tax_rate;
    
    /**
     * 契約解除条項
     */
    private String termination_clause;
    
    /**
     * 最低勤務時間
     */
    private BigDecimal working_hours_min;
    
    /**
     * 最大勤務時間
     */
    private BigDecimal working_hours_max;
    
    /**
     * 残業代が含まれるかどうか
     */
    private boolean overtime_included;
    
    /**
     * 残業代計算の開始時間
     */
    private BigDecimal overtime_start_time;
    
    /**
     * 最大残業時間
     */
    private BigDecimal overtime_limit_hours;
    
    /**
     * 更新时间
     */
    private LocalDateTime update_time;
    
    /**
     * 当前页码
     */
    private int currentPage;
    /**
     * 每页数量
     */
    private int pageSize;
    
}
