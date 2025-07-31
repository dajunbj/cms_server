package com.cms.module.expense.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.cms.module.expense.dto.ExpenseRequestDto;
import com.cms.module.expense.entity.ExpenseRequest;

public interface ExpenseService {
    void createExpenseRequest(ExpenseRequestDto dto);

    List<ExpenseRequest> getPendingExpenses();

    void approve(Long id);

    void reject(Long id);

    List<ExpenseRequest> getHistory(String startDate, String endDate);

    ResponseEntity<byte[]> exportCsv();
}
