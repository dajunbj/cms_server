package com.cms.module.salarynew.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.module.salarynew.dto.PageDTO;
import com.cms.module.salarynew.dto.SalaryDetailDto;
import com.cms.module.salarynew.dto.SalaryListItemDTO;
import com.cms.module.salarynew.dto.SalaryListQuery;
import com.cms.module.salarynew.mapper.SalaryDetailMapper;
import com.cms.module.salarynew.mapper.SalaryMapper;

@Service
public class SalaryService {

	@Autowired
    SalaryMapper salaryMapper;
	
	@Autowired
	SalaryDetailMapper mapper;
    /** 社員一覧 */
    public List<Map<String, Object>> listEmployees() {
        // 如果你没有 employee 表，可在 Mapper 里用 salarydetail 的 distinct 兜底
        return salaryMapper.listEmployees();
    }

    /** 単月の給与明細（YYYY-MM or YYYY-MM-01 どちらでも可） */
    public SalaryDetailDto getDetail(Integer employeeId, String salaryMonth) {
        if (employeeId == null || salaryMonth == null) return null;

        // 统一成 YYYY-MM-01
        final String ym = normalizeMonth(salaryMonth);

        SalaryDetailDto dto = salaryMapper.getDetail(employeeId, ym);

        // 若数据库没有该月记录，返回“空壳”，前端可显示0
        if (dto == null) {
            dto = new SalaryDetailDto();
            dto.employee_id = employeeId;
            dto.employee_code = String.valueOf(employeeId);
            dto.employee_name = "社員" + employeeId;
            dto.salary_month = ym;
        }

        // ====== 兜底汇总计算：当 total_* / net_payment 为空或为0 时自动计算 ======
        int totalPayment = nz(dto.total_payment);
        if (totalPayment == 0) {
            totalPayment =
                    nz(dto.base_salary) +
                    nz(dto.position_allowance) +
                    nz(dto.commuting_allowance) +
                    nz(dto.overtime_allowance) +
                    nz(dto.family_allowance) +
                    nz(dto.qualification_allowance)
                    // 以下为可选扩展列（表里没有就保持0）
                    + nz(dto.housing_allowance)
                    + nz(dto.midnight_allowance)
                    + nz(dto.holiday_allowance);
            dto.total_payment = totalPayment;
        }

        int totalDeduction = nz(dto.total_deduction);
        if (totalDeduction == 0) {
            totalDeduction =
                    nz(dto.health_insurance) +
                    nz(dto.welfare_pension) +
                    nz(dto.employment_insurance) +
                    nz(dto.nursing_insurance) +
                    nz(dto.income_tax) +
                    nz(dto.resident_tax);
            dto.total_deduction = totalDeduction;
        }

        int net = nz(dto.net_payment);
        if (net == 0) {
            dto.net_payment = totalPayment - totalDeduction;
        }

        return dto;
    }

    // ---------- helpers ----------
    private static int nz(Integer v) { return v == null ? 0 : v; }

    private static String normalizeMonth(String m) {
        // 允许传 '2025-08' 或 '2025-08-01'
        if (m == null) return null;
        if (m.length() == 7) return m + "-01";
        // 也可加更多格式兼容
        return m;
    }
    
    

    /** 排序白名单：前端传的 prop -> 数据库列 */
    private static final Map<String,String> SORT_WHITELIST = Map.of(
        "employeeCode", "employee_code",
        "employeeName", "employee_name",
        "companyName", "company_name",
        "departmentName", "department_name",
        "salaryMonth", "salary_month",
        "totalPayment", "total_payment",
        "totalDeduction", "total_deduction",
        "netPayment", "net_payment"
    );

    private String buildOrderBy(String prop, String order){
      String col = SORT_WHITELIST.getOrDefault(
          Optional.ofNullable(prop).orElse("salaryMonth"), "salary_month");
      String dir = "asc".equalsIgnoreCase(order) ? "ASC" : "DESC";
      return col + " " + dir;
    }

    public PageDTO<SalaryListItemDTO> list(SalaryListQuery q){
      int page = Optional.ofNullable(q.getPage()).orElse(1);
      int size = Optional.ofNullable(q.getSize()).orElse(20);
      page = Math.max(page, 1);
      size = Math.max(size, 1);

      LocalDate m = null;
      if(q.getSalaryMonth()!=null && !q.getSalaryMonth().isBlank()){
        m = LocalDate.parse(q.getSalaryMonth()); // 'YYYY-MM-01'
      }

      long total = mapper.countList(q.getCompanyId(), q.getDepartmentId(), q.getEmployeeId(), m);
      List<SalaryListItemDTO> items = Collections.emptyList();
      if(total > 0){
        String orderBy = buildOrderBy(q.getSort(), q.getOrder());
        int offset = (page-1)*size;
        items = mapper.selectList(q.getCompanyId(), q.getDepartmentId(), q.getEmployeeId(), m,
            orderBy, offset, size);
      }
      return new PageDTO<>(items, total);
    }

    public String exportCsv(SalaryListQuery q){
      // 导出不分页（可加上限）
      q.setPage(1); q.setSize(100000);
      var pg = list(q);

      List<String[]> rows = new ArrayList<>();
      rows.add(new String[]{"社員番号","氏名","会社","部署","年月","総支給額","総控除額","差引支給額"});

      for(var r: pg.getItems()){
        rows.add(new String[]{
            s(r.getEmployeeCode()),
            s(r.getEmployeeName()),
            s(r.getCompanyName()),
            s(r.getDepartmentName()),
            r.getSalaryMonth()==null ? "" : r.getSalaryMonth().substring(0,7),
            s(r.getTotalPayment()),
            s(r.getTotalDeduction()),
            s(r.getNetPayment())
        });
      }
      return toCsv(rows);
    }

    private String s(Object o){ return o==null? "" : String.valueOf(o); }
    private String toCsv(List<String[]> data){
      return data.stream()
          .map(arr -> Arrays.stream(arr).map(this::csvEsc).collect(Collectors.joining(",")))
          .collect(Collectors.joining("\n"));
    }
    private String csvEsc(String s){
      if(s==null) return "";
      if(s.contains("\"") || s.contains(",") || s.contains("\n")){
        return "\"" + s.replace("\"","\"\"") + "\"";
      }
      return s;
    }
}