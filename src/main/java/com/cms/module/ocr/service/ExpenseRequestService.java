package com.cms.module.ocr.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cms.module.ocr.entity.ExpenseRequest;
import com.cms.module.ocr.mapper.ExpenseReceiptLinkMapper;
import com.cms.module.ocr.mapper.ExpenseRequestMapper;
import com.cms.module.ocr.mapper.ReceiptInfoMapper;

@Service
public class ExpenseRequestService {

	@Autowired
	private ExpenseRequestMapper expenseRequestMapper;
	@Autowired
	private ExpenseReceiptLinkMapper linkMapper;
	@Autowired
	private ReceiptInfoMapper receiptInfoMapper;

	@Transactional
	public Long createRequest(Long applicantId, String summary, List<Long> receiptIds) {
		if (receiptIds == null || receiptIds.isEmpty())
			throw new IllegalArgumentException("receiptIds is empty");

		// 只能 草稿/確認済 才能申请
		int bad = receiptInfoMapper.countNotApplyable(receiptIds);
		if (bad > 0)
			throw new IllegalStateException("含有不可申請の領収書があります");

		Long total = expenseRequestMapper.sumReceiptAmounts(receiptIds);

		ExpenseRequest req = new ExpenseRequest();
		req.setApplicantId(applicantId);
		req.setSummary(summary);
		req.setStatus("申請済");
		req.setTotalAmount(total);
		req.setCreatedAt(LocalDateTime.now());
		expenseRequestMapper.insert(req);

		linkMapper.batchInsert(req.getId(), receiptIds);

		// 更新收据状态为 申請済
		receiptInfoMapper.updateStatusAppliedBatch(receiptIds);

		return req.getId();
	}
}
