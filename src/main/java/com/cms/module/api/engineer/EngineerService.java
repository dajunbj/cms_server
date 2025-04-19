package com.cms.module.api.engineer;

import java.util.Arrays;

import org.springframework.stereotype.Service;

import com.cms.module.api.engineer.entity.EngineerSharedInput;
import com.cms.module.api.engineer.entity.EngineerSharedOutput;

@Service
public class EngineerService {

    // 技術者共有APIのビジネスロジック
    public EngineerSharedOutput getSharedEngineer(EngineerSharedInput input) {
    	
        // 出力データの生成
        EngineerSharedOutput output = new EngineerSharedOutput();
        output.setTotal(2);
        output.setCompanyName("ABC Corp"); // 所属会社名の設定
        output.setResponsiblePerson("John Doe"); // 責任者名の設定
        output.setContactPhone("03-1234-5678"); // 責任者連絡先の設定

        // 技術者1のデータ作成
        EngineerSharedOutput.TechnicianData tech1 = new EngineerSharedOutput.TechnicianData();
        tech1.setTechnicianId(1);
        tech1.setName("Alice"); // 名前の設定
        tech1.setAge(30); // 年齢の設定
        tech1.setYearsOfWork(5); // 勤続年数の設定
        tech1.setStartDate("2020-01-01"); // 入社年月日の設定
        tech1.setHomeAddress("Tokyo"); // 家庭住所の設定
        tech1.setTechnicalDescription("Java, Spring Boot"); // 技術説明の設定

        // 技術者2のデータ作成
        EngineerSharedOutput.TechnicianData tech2 = new EngineerSharedOutput.TechnicianData();
        tech2.setTechnicianId(2);
        tech2.setName("Bob"); // 名前の設定
        tech2.setAge(35); // 年齢の設定
        tech2.setYearsOfWork(10); // 勤続年数の設定
        tech2.setStartDate("2015-01-01"); // 入社年月日の設定
        tech2.setHomeAddress("Osaka"); // 家庭住所の設定
        tech2.setTechnicalDescription("Python, Django"); // 技術説明の設定

        // データをリストに追加
        output.setData(Arrays.asList(tech1, tech2));
        return output;
    }
}