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
@Table(name = "case")
public class ApiBean {

	 @Id
	    @Column(name = "case_id")
	    private int case_id;

	    @Column(name = "customer_id")
	    private int customer_id;
	    
	    @Column(name = "employee_id")
	    private int employee_id;
	    
	    @Column(name = "case_type")
	    private String case_type;
	    
	    @Column(name = "responsible_id")
	    private int responsible_id;

	    @Column(name = "case_title")
	    private String case_title;
	    
	    @Column(name = "case_description")
	    private String case_description;
	    
	    @Column(name = "case_budget")
	    private String case_budget;
	    
	    @Column(name = "case_skill")
	    private String case_skill;
	    
	    @Column(name = "case_address")
	    private String case_address;

	    @Column(name = "start_date")
	    private Date start_date;
	    
	    @Column(name = "end_date")
	    private Date end_date;
	    
	    @Column(name = "case_status")
	    private String case_status;

	    @Column(name = "location")
	    private String location;
	    
	    @Column(name = "created_at")
	    private Date created_at;

	    @Column(name = "updated_at")
	    private Date updated_at;
	    
	    @Column(name = "share_company_id")
	    private int share_company_id;

	    
	    @Column(name = "valid_flg")
	    private Boolean valid_flg;
}










