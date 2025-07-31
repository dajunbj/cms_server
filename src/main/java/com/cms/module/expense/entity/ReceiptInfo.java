package com.cms.module.expense.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

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
