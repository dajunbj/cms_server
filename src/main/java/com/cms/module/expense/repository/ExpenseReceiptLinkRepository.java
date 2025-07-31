package com.cms.module.expense.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cms.module.expense.entity.ExpenseReceiptLink;

@Repository
public interface ExpenseReceiptLinkRepository extends JpaRepository<ExpenseReceiptLink, Long> {

}
