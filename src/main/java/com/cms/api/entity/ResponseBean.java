package com.cms.api.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

/**
 * 社員エンティティ
 */
@Data
@Table(name = "ResponseBean")
public class ResponseBean {
	
		@Id
	    @Column(name = "count")
	    private int count;

	    @Column(name = "company_name")
	    private String company_name;
	    
	    @Column(name = "name")
	    private String name;
	    
	    @Column(name = "phone_number")
	    private String phone_number;
	    
	    @Column(name = "error_message")
	    private String error_message;
	    
	    @Column(name = "case_id")
	    private int case_id;
	    
	    @Column(name = "case_title")
	    private String case_title;
	    
	    @Column(name = "case_description")
	    private String case_description;
	    
	    @Column(name = "case_budget")
	    private String case_budget;
	    
	    @Column(name = "start_date")
	    private Date start_date;
	    
	    @Column(name = "case_skill")
	    private String case_skill;
	    
	    @Column(name = "case_address")
	    private String case_address;
	    
	    @Column(name = "customer_name")
	    private String customer_name;
	    
	    @Column(name = "responsible_name")
	    private String responsible_name;
	    
	    @Column(name = "responsible_phone")
	    private String responsible_phone;
	    
}








