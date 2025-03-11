package com.cms.module.popup.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 社員エンティティ
 */
@Data
@Table(name = "customer_department")
public class DepartmentBean {

    @Id
    @Column(name = "department_id")
    private int department_id;

    @Column(name = "department_name")
    private String department_name;
    
}
