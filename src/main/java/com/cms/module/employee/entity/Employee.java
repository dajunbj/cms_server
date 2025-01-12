package com.cms.module.employee.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 社員エンティティ
 */
@Data
@Table(name = "cms_employee")
public class Employee {

    @Id
    @Column(name = "employeeId", nullable = false, length = 8)
    private String employeeId;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "sex", nullable = false, length = 2)
    private String sex;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Column(name = "address", nullable = false, length = 50)
    private String address;

    @Column(name = "phone", nullable = false, length = 12)
    private String phone;

    @Column(name = "joiningDate", nullable = false)
    private LocalDate joiningDate;

    @Column(name = "mail", nullable = false, length = 100)
    private String mail;

    @Column(name = "jobType", length = 4)
    private String jobType;

    @Column(name = "jobLevel", length = 4)
    private String jobLevel;

    @Column(name = "employeeKbn", length = 4)
    private String employeeKbn;

    @Column(name = "salary", nullable = false)
    private Double salary;

    @Column(name = "employeeType", length = 4)
    private String employeeType;

    @Column(name = "hasTax", length = 1)
    private String hasTax;

    @Column(name = "topWorkHour", nullable = false)
    private Integer topWorkHour;

    @Column(name = "downWorkHour", nullable = false)
    private Integer downWorkHour;

    @Column(name = "loginId", nullable = false, length = 50)
    private String loginId;

    @Column(name = "password", nullable = false, length = 50)
    private String password;

    @Column(name = "registDay", nullable = false)
    private LocalDateTime registDay;

    @Column(name = "updateDay", nullable = false)
    private LocalDateTime updateDay;
}
