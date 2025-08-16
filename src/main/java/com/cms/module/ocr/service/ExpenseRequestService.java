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

/**
 * 経費申請ドメインサービス
 * - 新規申請作成、リンク作成、ステータス更新を1トランザクションで行う。
 */
@Service
public class ExpenseRequestService {

    @Autowired
    private ExpenseRequestMapper expenseRequestMapper;

    @Autowired
    private ExpenseReceiptLinkMapper linkMapper;

    @Autowired
    private ReceiptInfoMapper receiptInfoMapper;

    /**
     * 申請作成（トランザクション境界）
     *
     * 手順:
     *  1) 入力チェック（空でないか）
     *  2) 申請不可な領収書が混在しないか検査（草稿/確認済のみ可）
     *  3) 合計金額を算出（receipt_info.amount の合計）
     *  4) expense_request をINSERT
     *  5) expense_receipt_link に一括INSERT
     *  6) 該当領収書の status を「申請済」に一括更新
     *
     * すべて成功した場合のみコミット、失敗すればロールバック。
     */
    @Transactional
    public Long createRequest(Long applicantId, String summary, List<Long> receiptIds) {
        // 1) 入力チェック
        if (receiptIds == null || receiptIds.isEmpty()) {
            throw new IllegalArgumentException("receiptIds is empty");
        }

        // 2) 申請不可な領収書（例: 既に申請済/承認済/差戻し等）が含まれていないか
        int bad = receiptInfoMapper.countNotApplyable(receiptIds);
        if (bad > 0) {
            throw new IllegalStateException("含有不可申請の領収書があります");
        }

        // 3) 合計金額
        //  ※ sumReceiptAmounts は NULL の可能性があるため 0L ガードでもOK（Mapper側で COALESCE しているなら不要）
        Long total = expenseRequestMapper.sumReceiptAmounts(receiptIds);
        if (total == null) total = 0L;

        // 4) 経費申請 INSERT
        ExpenseRequest req = new ExpenseRequest();
        req.setApplicantId(applicantId);
        req.setSummary(summary);
        req.setStatus("申請済");              // 初期ステータス
        req.setTotalAmount(total);           // 合計金額（円：整数想定）
        req.setCreatedAt(LocalDateTime.now());
        // reject_reason は申請作成時は null のまま
        expenseRequestMapper.insert(req);    // 自動採番IDが req.setId(...) に反映される前提の設定（useGeneratedKeys 等）

        // 5) 経費申請-領収書リンクを一括登録
        linkMapper.batchInsert(req.getId(), receiptIds);

        // 6) 領収書のステータスを一括で「申請済」に更新
        receiptInfoMapper.updateStatusAppliedBatch(receiptIds);

        return req.getId();
    }
}
