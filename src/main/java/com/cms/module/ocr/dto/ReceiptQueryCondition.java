package com.cms.module.ocr.dto;

public class ReceiptQueryCondition {
    private int offset;     // 例： (page-1)*size
    private int limit;      // 例： size
    private String storeName;
    private String status;

    public int getOffset() { return offset; }
    public void setOffset(int offset) { this.offset = offset; }

    public int getLimit() { return limit; }
    public void setLimit(int limit) { this.limit = limit; }

    public String getStoreName() { return storeName; }
    public void setStoreName(String storeName) { this.storeName = storeName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
