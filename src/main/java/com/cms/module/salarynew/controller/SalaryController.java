package com.cms.module.salarynew.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.module.salarynew.dto.ApiResp;
import com.cms.module.salarynew.dto.ApiResponse;
import com.cms.module.salarynew.dto.PageDTO;
import com.cms.module.salarynew.dto.SalaryDetailDto;
import com.cms.module.salarynew.dto.SalaryListItemDTO;
import com.cms.module.salarynew.dto.SalaryListQuery;
import com.cms.module.salarynew.service.OptionService;
import com.cms.module.salarynew.service.SalaryService;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/salarydetail")
public class SalaryController {
  @Autowired
  SalaryService service;
  @Autowired
  OptionService optionService;

  @PostMapping("/listEmployees")
  public ApiResponse<List<Map<String,Object>>> listEmployees(){
    return ApiResponse.ok(service.listEmployees());
  }

  @PostMapping("/getDetail")
  public ApiResponse<SalaryDetailDto> getDetail(@RequestBody GetDetailReq req){
    if(req.employee_id == null || req.salary_month == null){
      return ApiResponse.fail("employee_id / salary_month 必須");
    }
    return ApiResponse.ok(service.getDetail(req.employee_id, req.salary_month));
  }

  @GetMapping("/options")
  public ApiResp<Object> options(){
    return ApiResp.ok(optionService.loadOptions());
  }

  /*
   * 一覧画面・検索ボタン 
   */
  @PostMapping("/list")
  public ApiResp<PageDTO<SalaryListItemDTO>> list(@RequestBody SalaryListQuery q){
	  
	  //一覧検索
	  PageDTO<SalaryListItemDTO> result = service.list(q);
	  
    return ApiResp.ok(result);
  }
  
  /*
   * 一覧画面・CSV出力 
   */
  @PostMapping(value="/export", produces = "text/csv;charset=UTF-8")
  public ResponseEntity<String> export(@RequestBody SalaryListQuery q){
	  
    String csv = service.exportCsv(q);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.valueOf("text/csv;charset=UTF-8"));
    headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"salary_list.csv\"");
    
    return new ResponseEntity<>(csv, headers, HttpStatus.OK);
  }
  
  @Data
  public static class GetDetailReq{
    public Integer employee_id;
    public String salary_month; // 'YYYY-MM-01' or 'YYYY-MM'
  }
}
