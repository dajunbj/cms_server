package com.cms.module.employee.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.module.employee.entity.Employee;
import com.cms.module.employee.mapper.CmsEmployeeMapper;

/**
 * Employee のサービスクラス
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

	/**複数テーブル処理*/
	@Autowired
	CmsEmployeeMapper mapper;
	
    public Map<String, Object> findAllWithPagination(Map<String, Object> conditions) {

    	int recordCount = mapper.selectCount(conditions);
    	
    	List<Employee> results = mapper.select(conditions);
    	
        Map<String, Object> result = new HashMap<>();
        result.put("data", results); // 当前页数据
        result.put("total", recordCount); // 总条目数
        return result;

    }

	@Override
	public void saveEmployee(Employee employee) {

		mapper.insertEmployee(employee);
	}
	
	  /**
     * 根据ID获取员工详情
     *
     * @param id 员工ID
     * @return 员工详情
     */
    @Override
    public Employee findById(String id) {
        return mapper.findById(id);
    }

    /**
     * 更新员工信息
     *
     * @param employee 员工实体
     * @return 更新是否成功
     */
    @Override
    public boolean updateEmployee(Employee employee) {
        return mapper.updateEmployee(employee) > 0;
    }
 
//    /**
//     * IDでエンティティを削除
//     * @param id エンティティのID
//     * @return 削除成功かどうか
//     */
//    @Override
//    public boolean deleteById(Long id) {
//        return employeeMapper.deleteById(id) > 0;
//    }

    
}
