package com.cms.module.contract.entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 社員エンティティ
 */
@Data
@Table(name = "contracts")
public class ContractBean {

    @Id
    @Column(name = "contract_id")
    private int contract_id;

    @Column(name = "custom_id")
    private int custom_id;

    @Column(name = "employee_id")
    private int employee_id;
    
    @Column(name = "responsible_id")
    private int responsible_id;
    
    @Column(name = "case_id")
    private int case_id;

    @Column(name = "name")
    private String name;
    
    @Column(name = "phone_number")
    private int phone_number;
    
    @Column(name = "customer_name")
    private String customer_name;
    
    @Column(name = "responsible_name")
    private String responsible_name;
    
    @Column(name = "employee_type")
    private String employee_type;

    @Column(name = "start_date")
    private Date start_date;

    @Column(name = "end_date")
    private Date end_date;

    @Column(name = "unit_price")
    private BigDecimal unit_price;

    @Column(name = "tax_rate")
    private BigDecimal tax_rate;

    @Column(name = "termination_clause")
    private String termination_clause;

    @Column(name = "working_hours_min")
    private BigDecimal working_hours_min;

    @Column(name = "working_hours_max")
    private BigDecimal working_hours_max;

    @Column(name = "overtime_included")
    private boolean overtime_included;

    @Column(name = "overtime_start_time")
    private BigDecimal overtime_start_time;

    @Column(name = "overtime_limit_hours")
    private BigDecimal overtime_limit_hours;
    
    @Column(name = "update_time")
    private LocalDateTime update_time;
    
    @Column(name = "customer_phone")
    private String customer_phone;
    
    @Column(name = "responsible_phone")
    private String responsible_phone;

}
