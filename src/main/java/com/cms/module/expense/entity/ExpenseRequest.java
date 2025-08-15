package com.cms.module.expense.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "expense_request")
public class ExpenseRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long applicantId;

    private String summary;

    private String status; // 申請済 / 承認済 / 差し戻し

    private Long approvedBy;

    private LocalDateTime approvedAt;

    private Double totalAmount;

    private LocalDateTime createdAt;
}
