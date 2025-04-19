package com.cms.module.api.projects.entity;

import java.util.List;

import lombok.Data;

//プロジェクト共有APIの出力Bean
@Data
public class ProjectSharedOutput {
 // 総件数
 private Integer total;
 // 所属会社名
 private String companyName;
 // 責任者名
 private String responsiblePerson;
 // 責任者連絡先
 private String contactPhone;
 // プロジェクトリスト
 private List<ProjectData> data;

 // プロジェクト情報
 @Data
 public static class ProjectData {
     // プロジェクトID
     private Integer projectId;
     // プロジェクトタイトル
     private String title;
     // プロジェクト説明
     private String description;
     // プロジェクト予算
     private String budget;
     // 案件開始月
     private String startMonth;
     // 技術要件
     private String technicalRequirements;
     // 作業場所
     private String location;
     // 情報提供元
     private SourceInfo sourceInfo;

     // 情報提供元の詳細
     @Data
     public static class SourceInfo {
         // クライアント名
         private String clientName;
         // クライアント担当者名
         private String clientContactPerson;
         // クライアント連絡先
         private String clientContactPhone;
     }
 }
}