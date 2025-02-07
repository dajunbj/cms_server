package com.cms.module.api.engineer.entity;

import java.util.List;

import lombok.Data;

//技術者共有APIの出力Bean
@Data
public class EngineerSharedOutput {
 // 総件数
 private Integer total;
 // 所属会社名
 private String companyName;
 // 責任者名
 private String responsiblePerson;
 // 責任者連絡先
 private String contactPhone;
 // 技術者リスト
 private List<TechnicianData> data;

 // 技術者情報
 @Data
 public static class TechnicianData {
     // 技術者ID
     private Integer technicianId;
     // 技術者名
     private String name;
     // 年齢
     private Integer age;
     // 勤続年数
     private Integer yearsOfWork;
     // 入社年月日
     private String startDate;
     // 家庭住所
     private String homeAddress;
     // 技術説明
     private String technicalDescription;
 }
}