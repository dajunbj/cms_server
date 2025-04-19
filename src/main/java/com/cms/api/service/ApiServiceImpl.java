package com.cms.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.api.entity.ApiBean;
import com.cms.api.entity.ResponseBean;
import com.cms.api.form.ApiForm;
import com.cms.api.mapper.ApiMapper;

/**
 * Employees のサービスクラス
 */
@Service
public class ApiServiceImpl implements ApiService {

	/**複数テーブル処理*/
	@Autowired
	ApiMapper mapper;
	
	/**
     * 删除处理
     *
     * @param contract_id
     * @return 处理结果
     */
	public int shareCheck(int id){
		int result;
		ApiBean response = mapper.shareCheck(id);
		if(response ==null || response.getShare_company_id()==0 ) {
			result = 1;
		}
		else {
			if(!response.getValid_flg()) {
				result = 2;
			}
			else {
				result = 0;
			}
		}
		return result;
	}
    
	public List<ResponseBean> search (ApiForm input) {
		List<ResponseBean> results = mapper.search(input);
		return results;
	}
}










