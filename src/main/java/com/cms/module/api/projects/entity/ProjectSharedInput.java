package com.cms.module.api.projects.entity;

import lombok.Data;
//プロジェクト共有APIの入力Bean
@Data
public class ProjectSharedInput {
    // プロジェクト開始日
    private String startDate;
    // プロジェクト終了日
    private String endDate;
}