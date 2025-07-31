package com.cms.module.expense.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cms.module.expense.dto.ExpenseRequestDto;
import com.cms.module.expense.service.ExpenseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping("/apply")
    public ResponseEntity<?> applyExpense(@RequestBody ExpenseRequestDto dto) {
        expenseService.createExpenseRequest(dto);
        return ResponseEntity.ok(Map.of("message", "申請成功"));
    }

    @GetMapping("/pending")
    public ResponseEntity<?> getPendingExpenses() {
        return ResponseEntity.ok(expenseService.getPendingExpenses());
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<?> approve(@PathVariable Long id) {
        expenseService.approve(id);
        return ResponseEntity.ok(Map.of("message", "承認成功"));
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<?> reject(@PathVariable Long id) {
        expenseService.reject(id);
        return ResponseEntity.ok(Map.of("message", "差し戻し済み"));
    }

    @GetMapping("/history")
    public ResponseEntity<?> getHistory(
            @RequestParam(required = false) String start_date,
            @RequestParam(required = false) String end_date
    ) {
        return ResponseEntity.ok(expenseService.getHistory(start_date, end_date));
    }

    @GetMapping("/export")
    public ResponseEntity<?> exportCsv() {
        return expenseService.exportCsv();
    }
}
