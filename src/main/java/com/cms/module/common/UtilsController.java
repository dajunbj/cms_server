package com.cms.module.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.module.common.dto.CompanyBean;

/**
 * 共通
 */
@RestController
@RequestMapping("/utils")
public class UtilsController {

	
	@PostMapping("/SearchCompanyList")
	public List<CompanyBean> searchCompanyList(@RequestBody CompanyBean request) {

		
	    List<CompanyBean> result = new ArrayList<CompanyBean>();
	    CompanyBean bean;
	    for (int i = 0; i < 3; i++) {
	        bean = new CompanyBean();
	        bean.setID(String.valueOf(i + 1));
	        bean.setName(request.getName() + (i + 1));
	        bean.setDepartment(request.getDepartment() + (i + 1));
	        result.add(bean);
	    }
	    return result;
	}

}