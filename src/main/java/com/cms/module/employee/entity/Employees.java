package com.cms.module.employee.entity;
import java.time.LocalDate;

import lombok.Data;
@Data
public class Employees {

    private Integer employee_id;
    private Integer company_id;
    private Integer department_id;

    private Integer companyId;
    private String name;
    private String email;              
    private String password_hash;    
    private String role;            
    private String status;           

    private LocalDate hire_date;
    private LocalDate resignation_date;
    private String gender;
    private LocalDate date_of_birth;
    private String phone_number;

    private String bank_account_name;
    private String bank_account_number;
    private String bank_name;
    private String branch_name;

    private String residence_card_number;
    private String residence_status;
    private LocalDate residence_expiry_date_from;
    private LocalDate residence_expiry_date_end;

    private String login_id;
    private String pwd;
    private String plan_code;
    private String auth_code;
    private String user_role;

    private LocalDate created_at;
    private LocalDate updated_at;
}
