package com.cms.module.employee.entity;
import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class Employees {

    private Integer employee_id;
    private String name;
    private String sex;
    private Integer work_years;
    private String specialty_skills;
    private Integer age;
    private String address;
    private String technic_description;
    private String employee_type;
    private Integer company_id;
    private Integer bp_company_id;
    private Integer department_id;
    private LocalDate hire_date;
    private LocalDate resignation_date;
    private String gender;
    private LocalDate date_of_birth;
    private String phone_number;
    private String email;
    private String position;
    private BigDecimal salary;
    private BigDecimal bonus;
    private String contract_type;
    private String emergency_contact;
    private String emergency_phone;
    private String bank_account_name;
    private String bank_account_number;
    private String bank_name;
    private String branch_name;
    private String employment_status;
    private LocalDate work_permit_expiry;
    private String nationality;
    private String residence_card_number;
    private String residence_status;
    private LocalDate residence_expiry_date_from;
    private LocalDate residence_expiry_date_end;
    private Integer role_id;
    private String my_number;
    private String login_id;
    private String pwd;
}