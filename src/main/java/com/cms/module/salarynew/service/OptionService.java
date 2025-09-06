package com.cms.module.salarynew.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.cms.module.salarynew.mapper.SalaryDetailMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OptionService {

  private final SalaryDetailMapper mapper;

  public Map<String,Object> loadOptions(){
    var companies = mapper.distinctCompanies();   // [{id:..., name:...}]
    var departments = mapper.distinctDepartments();
    var employees = mapper.distinctEmployees();
    return Map.of("companies", companies, "departments", departments, "employees", employees);
  }
}
