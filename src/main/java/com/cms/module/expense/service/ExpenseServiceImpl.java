package com.cms.module.expense.service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cms.module.expense.dto.ExpenseRequestDto;
import com.cms.module.expense.entity.ExpenseReceiptLink;
import com.cms.module.expense.entity.ExpenseRequest;
import com.cms.module.expense.entity.ReceiptInfo;
import com.cms.module.expense.repository.ExpenseReceiptLinkRepository;
import com.cms.module.expense.repository.ExpenseRequestRepository;
import com.cms.module.expense.repository.ReceiptInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRequestRepository expenseRequestRepository;
    private final ReceiptInfoRepository receiptInfoRepository;
    private final ExpenseReceiptLinkRepository expenseReceiptLinkRepository;

    @Override
    public void createExpenseRequest(ExpenseRequestDto dto) {
        List<ReceiptInfo> receipts = receiptInfoRepository.findByIdIn(dto.getReceiptIds());
        double totalAmount = receipts.stream().mapToDouble(r -> r.getAmount() != null ? r.getAmount() : 0.0).sum();

        ExpenseRequest request = new ExpenseRequest();
        request.setApplicantId(1L); // TODO: セッションや認証情報から取得する
        request.setSummary(dto.getSummary());
        request.setStatus("申請済");
        request.setCreatedAt(LocalDateTime.now());
        request.setTotalAmount(totalAmount);
        ExpenseRequest saved = expenseRequestRepository.save(request);

        // link 保存
        List<ExpenseReceiptLink> links = receipts.stream()
            .map(r -> {
                ExpenseReceiptLink link = new ExpenseReceiptLink();
                link.setExpenseRequestId(saved.getId());
                link.setReceiptId(r.getId());
                return link;
            }).collect(Collectors.toList());
        expenseReceiptLinkRepository.saveAll(links);
    }

    @Override
    public List<ExpenseRequest> getPendingExpenses() {
        return expenseRequestRepository.findByStatus("申請済");
    }

    @Override
    public void approve(Long id) {
        ExpenseRequest request = expenseRequestRepository.findById(id).orElseThrow();
        request.setStatus("承認済");
        request.setApprovedBy(1L); // TODO: 認証ユーザーから取得
        request.setApprovedAt(LocalDateTime.now());
        expenseRequestRepository.save(request);
    }

    @Override
    public void reject(Long id) {
        ExpenseRequest request = expenseRequestRepository.findById(id).orElseThrow();
        request.setStatus("差し戻し");
        expenseRequestRepository.save(request);
    }

    @Override
    public List<ExpenseRequest> getHistory(String startDate, String endDate) {
        return expenseRequestRepository.findAll(); // TODO: フィルター追加可能
    }

    @Override
    public ResponseEntity<byte[]> exportCsv() {
        List<ExpenseRequest> data = expenseRequestRepository.findAll();
        StringBuilder csv = new StringBuilder("申請ID,摘要,金額,状態,作成日\n");
        for (ExpenseRequest r : data) {
            csv.append(r.getId()).append(",")
               .append(r.getSummary()).append(",")
               .append(r.getTotalAmount()).append(",")
               .append(r.getStatus()).append(",")
               .append(r.getCreatedAt()).append("\n");
        }
        byte[] content = csv.toString().getBytes(StandardCharsets.UTF_8);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=expenses.csv");
        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }
}
