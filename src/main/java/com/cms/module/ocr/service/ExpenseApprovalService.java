package com.cms.module.ocr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.module.ocr.dto.ReceiptInfoListItem;
import com.cms.module.ocr.entity.ExpenseRequest;
import com.cms.module.ocr.mapper.ExpenseRequestMapper;

/**
 * 経費承認サービス
 * - 申請一覧・件数取得
 * - 申請詳細（領収書リスト付き）
 * - 承認／差戻し処理
 *
 * 注意:
 *  - 状態遷移はDB側の update 条件で制御（例: status='申請済' の場合のみ更新成功）
 *  - 成功/失敗は戻り値の更新件数で判断
 */
@Service
public class ExpenseApprovalService {

    @Autowired
    private ExpenseRequestMapper expenseRequestMapper;

    /** 申請一覧取得（ページング対応） */
    public List<ExpenseRequest> list(String status, int page, int size, Long applicantId) {
        int offset = Math.max(0, (page - 1) * size);
        int limit = Math.max(1, size);
        return expenseRequestMapper.list(status, offset, limit, applicantId);
    }

    /** 申請件数取得 */
    public int count(String status, Long applicantId) {
        return expenseRequestMapper.count(status, applicantId);
    }

    /** ID指定で申請を1件取得 */
    public ExpenseRequest findById(Long id) {
        return expenseRequestMapper.findById(id);
    }

    /** 申請に紐づく領収書一覧を取得 */
    public List<ReceiptInfoListItem> findReceipts(Long requestId) {
        return expenseRequestMapper.findReceiptsByRequestId(requestId);
    }

    /** 承認処理（DB更新件数が1以上なら成功） */
    public boolean approve(Long id, Long approver) {
        return expenseRequestMapper.approve(id, approver) > 0;
    }

    /** 差戻し処理（DB更新件数が1以上なら成功） */
    public boolean reject(Long id, Long approver, String reason) {
        return expenseRequestMapper.reject(id, approver, reason) > 0;
    }
}
