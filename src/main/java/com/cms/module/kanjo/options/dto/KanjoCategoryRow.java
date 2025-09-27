// src/main/java/com/cms/module/kanjo/options/dto/KanjoCategoryRow.java
package com.cms.module.kanjo.options.dto;

import lombok.Data;

@Data
public class KanjoCategoryRow {
    private Long id;
    private String code;
    private String parentCode; // 由SQL算出（父记录的code）
    private String nameJa;
    private String nameZh;
    private Integer levelNo;
    private Integer sortNo;
}
