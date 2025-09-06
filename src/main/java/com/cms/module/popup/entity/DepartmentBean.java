package com.cms.module.popup.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
