package com.cms.module.expense.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cms.module.expense.entity.ExpenseRequest;

@Repository
public interface ExpenseRequestRepository extends JpaRepository<ExpenseRequest, Long> {

    List<ExpenseRequest> findByStatus(String status);

}
