package com.cms.module.employee.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.module.employee.entity.Employees;
import com.cms.module.employee.mapper.EmployeesMapper;

/**
 * Employee のサービスクラス
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

	/**複数テーブル処理*/
	@Autowired
	EmployeesMapper mapper;
	


	@Override
	public int selectCouint(Map<String, Object> conditions) {
		return mapper.selectCount(conditions);
	}
	
	/**
	 * 社員検索処理
	 * 
	 * @param conditions 検索条件
	 * @return 検索欠課
	 **/

    public List<Employees> searchAllData(Map<String, Object> conditions) {
    	
    	List<Employees> results = mapper.select(conditions);
   
        return results;
    }

	
	  /**
     * 根据ID获取员工详情
     *
     * @param id 员工ID
     * @return 员工详情
     */
    public Employees searchSingleById(String id) {
        return mapper.selectOne(id);
    }

    /**
     * 更新员工信息
     *
     * @param employee 员工实体
     * @return 更新是否成功
     */
    public void updateData(Employees employee) {
    	
        mapper.update(employee);
    }


	public void registData(Employees obj) {
		
	}


	public void deleteDataById(Employees obj) {
		
	}

	@Override
	public void deleteAll(List<Integer> ids) {

		mapper.deleteByIds(ids);

	}

	@Override
	public void initEdit(String id) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void initDetail(String id) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}
