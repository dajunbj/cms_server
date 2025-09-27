package com.cms.module.kanjo.options.vo;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CascaderNode {
    private String label;       // 展示名
    private String value;       // 建议=code（稳定）
    private String code;        // 备查
    private String parentCode;  // 备查
    private Integer sortNo;

    private Boolean isLeaf;     // 懒加载用（当前未用）
    private List<CascaderNode> children = new ArrayList<>();
}
