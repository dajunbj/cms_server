package com.cms.module.expense.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cms.module.expense.entity.ReceiptInfo;

@Repository
public interface ReceiptInfoRepository extends JpaRepository<ReceiptInfo, Long> {

    List<ReceiptInfo> findByStatus(String status);

    List<ReceiptInfo> findByIdIn(List<Long> ids);
}
