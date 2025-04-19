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
@Table(name = "customer")
public class CustomerBean {

    @Id
    @Column(name = "customer_id")
    private int customer_id;

    @Column(name = "customer_name")
    private String customer_name;

    @Column(name = "customer_email")
    private String customer_email;
    
    @Column(name = "customer_phone")
    private String customer_phone;
    
    @Column(name = "customer_type")
    private String customer_type;

    @Column(name = "customer_represent")
    private String customer_represent;
    
    @Column(name = "customer_address")
    private String customer_address;
    
    @Column(name = "customer_fax")
    private String customer_fax;
    
    @Column(name = "customer_mail")
    private String customer_mail;
    
    @Column(name = "deleteFlag")
    private Boolean deleteFlag;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "employee_id")
    private int employee_id;

}
