package com.cms.module.expense.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "receipt_info")
public class ReceiptInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imagePath;

    private String storeName;

    private LocalDate issueDate;

    private Double amount;

    @Lob
    private String fullText;

    private Long userId;

    private String status; // 草稿 / 確認済 / 申請済 / 承認済 / 差し戻し

    private LocalDateTime createdAt;
}
