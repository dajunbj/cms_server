package com.cms.module.ocr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.module.ocr.dto.ReceiptInfoListItem;
import com.cms.module.ocr.dto.ReceiptQueryCondition;
import com.cms.module.ocr.mapper.ReceiptInfoMapper;

/**
 * 領収書一覧・集計専用サービス。
 * - OCR実行・保存ロジックは OcrReadService に分離。
 * - 本クラスは「検索・集計ロジック」のみ担当する。
 */
@Service
public class ReceiptQueryService {

    @Autowired
    private ReceiptInfoMapper receiptInfoMapper;

    /**
     * 条件付きで領収書の一覧を取得（ページング対応）
     *
     * @param cond 検索条件（offset, limit, storeName, status）
     * @return 該当する領収書のリスト
     */
    public List<ReceiptInfoListItem> queryListByCondition(ReceiptQueryCondition cond) {
        // null防止 & 境界処理
        if (cond == null) {
            throw new IllegalArgumentException("condition must not be null");
        }
        if (cond.getLimit() <= 0) {
            cond.setLimit(10); // デフォルト件数
        }
        if (cond.getOffset() < 0) {
            cond.setOffset(0);
        }
        return receiptInfoMapper.queryByCondition(cond);
    }

    /**
     * 条件付きで領収書件数をカウント
     *
     * @param cond 検索条件（storeName, status）
     * @return 件数
     */
    public int countByCondition(ReceiptQueryCondition cond) {
        if (cond == null) {
            throw new IllegalArgumentException("condition must not be null");
        }
        return receiptInfoMapper.countByCondition(cond);
    }
}
