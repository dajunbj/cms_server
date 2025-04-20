package com.cms.module.employee.service;

import java.util.HashMap;
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
	 * @return 検索結果
	 **/
    public List<Employees> searchAllData(Map<String, Object> conditions) {
    	
    	List<Employees> results = mapper.select(conditions);

        return results;
    }

	
    /**
     * 社員IDをキーにし、社員情報を抽出する
     *
     * @param id 社員ID
     * @return 社員情報
     */
    public Employees searchSingleById(String id) {
    	
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("employee_id", id);
    	
    	return mapper.selectOne(param);
    }

    /**
     * 社員情報更新
     *
     * @param employee 社員情報
     */
    public void updateData(Employees info) {
    	
        mapper.update(info);
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
