package com.cms.module.ocr.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.module.ocr.service.ExpenseRequestService;

@RestController
@RequestMapping("/api/expense/requests")
public class ExpenseRequestController {

	@Autowired
	private ExpenseRequestService expenseRequestService;

	@PostMapping
	public ResponseEntity<?> create(@RequestBody Map<String, Object> body,
			@RequestHeader(value = "X-User-Id", required = false) String userId) {
		try {
			if (userId == null || userId.isBlank())
				userId = "1";
			@SuppressWarnings("unchecked")
			List<Integer> idsInt = (List<Integer>) body.get("receiptIds");
			String summary = (String) body.getOrDefault("summary", "");
			// 转 Long
			List<Long> receiptIds = idsInt.stream().map(Integer::longValue).map(Long::valueOf).toList();

			Long reqId = expenseRequestService.createRequest(Long.valueOf(userId), summary, receiptIds);
			return ResponseEntity.ok(Map.of("id", reqId));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
		}
	}
}
