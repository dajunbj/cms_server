package com.cms.module.popup.form;

import lombok.Data;

/**
 * 社員エンティティクラス
 */
@Data
public class DepartmentForm {

    /**
     * 部門ID
     */
    private int department_id;
    
    /**
     * 部門名
     */
    private String department_name;
    
}
