package com.cms.module.employee.service;

import com.cms.base.service.BaseService;
import com.cms.module.employee.entity.Employees;

/**
 * 共通サービス層の基底抽象クラス。
 * 基本的なCRUDメソッドを提供し、必要に応じてオーバーライド可能。
 * @param <T> エンティティの型
 */
public interface EmployeeService extends BaseService<Employees, String> {

}
