package com.cms.module.ocr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.module.ocr.dto.ReceiptInfoListItem;
import com.cms.module.ocr.dto.ReceiptQueryCondition;
import com.cms.module.ocr.mapper.ReceiptInfoMapper;

/**
 * 領収書の一覧・集計に専念するサービス。
 * OCRや保存ロジックは OcrReadService に集約。
 */
@Service
public class ReceiptQueryService {

    @Autowired
    private ReceiptInfoMapper receiptInfoMapper;

    /**
     * 条件付き一覧取得（ページング）
     */
    public List<ReceiptInfoListItem> queryListByCondition(ReceiptQueryCondition cond) {
        // ガード（最小限の境界調整）
        if (cond == null) {
            throw new IllegalArgumentException("condition must not be null");
        }
        if (cond.getLimit() <= 0) {
            cond.setLimit(10);
        }
        if (cond.getOffset() < 0) {
            cond.setOffset(0);
        }
        return receiptInfoMapper.queryByCondition(cond);
    }

    /**
     * 条件付き総件数
     */
    public int countByCondition(ReceiptQueryCondition cond) {
        if (cond == null) {
            throw new IllegalArgumentException("condition must not be null");
        }
        return receiptInfoMapper.countByCondition(cond);
    }
}
