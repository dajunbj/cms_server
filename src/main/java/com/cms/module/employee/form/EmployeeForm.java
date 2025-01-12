package com.cms.module.employee.form;

import lombok.Data;

/**
 * 社員エンティティクラス
 */
@Data
public class EmployeeForm {

    /**
     * 社員ID
     */
    private String employeeId;

    /**
     * 社員名
     */
    private String employeeName;
    /**
     * メール
     */
    private String email;
    /**
     * 部門
     */
    private String department;
    
    /**
     * 当前页码
     */
    private int currentPage;
    /**
     * 每页数量
     */
    private int pageSize;

}
