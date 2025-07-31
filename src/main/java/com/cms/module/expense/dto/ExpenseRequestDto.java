package com.cms.module.expense.dto;

import java.util.List;

import lombok.Data;

@Data
public class ExpenseRequestDto {
    private String summary;              // 経費申請の摘要
    private List<Long> receiptIds;       // 関連する領収書IDのリスト
}
