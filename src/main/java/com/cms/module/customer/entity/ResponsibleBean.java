package com.cms.module.customer.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 社員エンティティ
 */
@Data
@Table(name = "responsible")
public class ResponsibleBean {

    @Id
    @Column(name = "responsible_id")
    private int responsible_id;
    
    @Column(name = "customer_id")
    private int customer_id;
    
    @Column(name = "customer_name")
    private String customer_name;
    
    @Column(name = "department_id")
    private int department_id;
    
    @Column(name = "employee_id")
    private int employee_id;

    @Column(name = "responsible_name")
    private String responsible_name;

    @Column(name = "responsible_email")
    private String responsible_email;
    
    @Column(name = "responsible_phone")
    private String responsible_phone;
    
    @Column(name = "responsible_type")
    private String responsible_type;

    @Column(name = "deleteFlag")
    private Boolean deleteFlag;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "department_name")
    private String department_name;
    
}
