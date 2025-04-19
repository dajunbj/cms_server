package com.cms;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cms.module.contract.entity.ContractBean;
import com.cms.module.contract.mapper.ContractMapper;
import com.cms.module.contract.service.ContractServiceImpl;

@SuppressWarnings("rawtypes")
@RunWith(SpringRunner.class)
@SpringBootTest
public class ContractServiceTest{
	
	@InjectMocks
	private ContractServiceImpl service;
	
	@Mock
	private ContractMapper mapper;
	
	
	private ContractBean createMockContract(int contractId, String name, String customerName, String employee_type,String responsible_name) {
	    ContractBean contract = new ContractBean();
	    contract.setContract_id(contractId);
	    contract.setName(name);
	    contract.setCustomer_name(customerName);
	    contract.setEmployee_type(employee_type);
	    contract.setStart_date(Date.valueOf("2025-01-01"));
	    contract.setEnd_date(Date.valueOf("2025-12-31"));
	    contract.setUnit_price(BigDecimal.valueOf(1000));
	    contract.setResponsible_name(responsible_name);
	    return contract;
	    }
	
	@Test
	public void findAllWithPagination() {
		int recordCount = 10;
		List<ContractBean> mockResults = Arrays.asList(
           createMockContract(1,"name 1","customerName1","type1","responsibleName1"),
           createMockContract(2,"name 2","customerName2","type2","responsibleName2")
		);
		
		when(mapper.selectCount(Mockito.any())).thenReturn(recordCount);
		when(mapper.select(Mockito.any())).thenReturn(mockResults);
		
		Map<String, Object> result = service.findAllWithPagination(Map.of());
		
		assertNotNull(result);
		assertEquals(10,result.get("total"));
		assertEquals(mockResults,result.get("data"));
		
		verify(mapper,times(1)).selectCount(Mockito.any());
		verify(mapper,times(1)).select(Mockito.any());
	}

}















