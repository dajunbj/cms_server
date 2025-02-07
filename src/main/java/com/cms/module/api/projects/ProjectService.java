package com.cms.module.api.projects;
import java.util.Arrays;

import org.springframework.stereotype.Service;

import com.cms.module.api.projects.entity.ProjectSharedInput;
import com.cms.module.api.projects.entity.ProjectSharedOutput;

@Service
public class ProjectService {

    // プロジェクト共有APIのビジネスロジック
    public ProjectSharedOutput getProjects(ProjectSharedInput input) {
        // 出力データの生成
        ProjectSharedOutput output = new ProjectSharedOutput();
        output.setTotal(2); // 総件数の設定
        output.setCompanyName("XYZ Solutions"); // 所属会社名の設定
        output.setResponsiblePerson("Jane Smith"); // 責任者名の設定
        output.setContactPhone("06-9876-5432"); // 責任者連絡先の設定

        // プロジェクト1のデータ作成
        ProjectSharedOutput.ProjectData proj1 = new ProjectSharedOutput.ProjectData();
        proj1.setProjectId(101); // プロジェクトIDの設定
        proj1.setTitle("Web Development"); // プロジェクトタイトルの設定
        proj1.setDescription("Develop a web application"); // プロジェクト説明の設定
        proj1.setBudget("500000-1000000"); // プロジェクト予算の設定
        proj1.setStartMonth("2023-01"); // 案件開始月の設定
        proj1.setTechnicalRequirements("Java, Spring Boot"); // 技術要件の設定
        proj1.setLocation("Tokyo"); // 作業場所の設定

        // 情報提供元1のデータ作成
        ProjectSharedOutput.ProjectData.SourceInfo source1 = new ProjectSharedOutput.ProjectData.SourceInfo();
        source1.setClientName("ABC Corp"); // クライアント名の設定
        source1.setClientContactPerson("John Doe"); // クライアント担当者名の設定
        source1.setClientContactPhone("03-1234-5678"); // クライアント連絡先の設定
        proj1.setSourceInfo(source1); // プロジェクト1に情報提供元を設定

        // プロジェクト2のデータ作成
        ProjectSharedOutput.ProjectData proj2 = new ProjectSharedOutput.ProjectData();
        proj2.setProjectId(102); // プロジェクトIDの設定
        proj2.setTitle("Data Analysis"); // プロジェクトタイトルの設定
        proj2.setDescription("Build a data analysis platform"); // プロジェクト説明の設定
        proj2.setBudget("1000000-2000000"); // プロジェクト予算の設定
        proj2.setStartMonth("2023-03"); // 案件開始月の設定
        proj2.setTechnicalRequirements("Python, Pandas"); // 技術要件の設定
        proj2.setLocation("Osaka"); // 作業場所の設定

        // 情報提供元2のデータ作成
        ProjectSharedOutput.ProjectData.SourceInfo source2 = new ProjectSharedOutput.ProjectData.SourceInfo();
        source2.setClientName("DEF Corp"); // クライアント名の設定
        source2.setClientContactPerson("Jane Smith"); // クライアント担当者名の設定
        source2.setClientContactPhone("06-9876-5432"); // クライアント連絡先の設定
        proj2.setSourceInfo(source2); // プロジェクト2に情報提供元を設定

        // 出力データにプロジェクトリストを設定
        output.setData(Arrays.asList(proj1, proj2));
        return output; // 出力データを返却
    }
}
