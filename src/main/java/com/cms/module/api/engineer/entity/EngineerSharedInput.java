package com.cms.module.api.engineer.entity;

import lombok.Data;

//技術者共有APIの入力Bean
@Data
public class EngineerSharedInput {
    // 会社ID
    private Integer companyId;
    // 技術スキル
    private String skill;
    // 利用可能日
    private String availability;
    // 作業場所
    private String location;
}