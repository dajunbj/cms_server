package com.cms.module.contract.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 社員エンティティ
 */
@Data
@Table(name = "case")
public class CaseBean {

    @Id
    @Column(name = "case_id")
    private int case_id;

    @Column(name = "customer_id")
    private int customer_id;

    @Column(name = "case_title")
    private String case_title;
    
    @Column(name = "case_description")
    private String case_description;

    @Column(name = "start_date")
    private Date start_date;
    
    @Column(name = "end_date")
    private Date end_date;
    
    @Column(name = "case_status")
    private String case_status;

    @Column(name = "created_at")
    private Date created_at;

    @Column(name = "updated_at")
    private Date updated_at;

}
