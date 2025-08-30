package com.cms.module.print.controller;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.base.controller.BaseController;
import com.cms.module.print.service.printService;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;

@RestController
//@CrossOrigin(origins = "http://localhost:8081") // 允许从指定域访问
@RequestMapping("/print")
public class printController extends BaseController{

	@Autowired
	private printService service;
	
    /**
     * HASH
     * 
     * @param loginData 
     * @return Token情報
     */

	
	
    /**
     * ログインロジック
     * 
     * @param loginData 
     * @return Token情報
     */
    @PostMapping("/printPDF")
    public void login(@RequestBody Map<String, Object> loginData) {

    	 try {
    		 
    		 GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    		 ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/fonts/NotoSansCJKjp.ttf")));
    		 
    		 
    		 JRPropertiesUtil.getInstance(DefaultJasperReportsContext.getInstance())
    		    .setProperty("net.sf.jasperreports.awt.ignore.missing.font", "true");
    		 
    	        // 1. 读取 jrxml 文件并编译
    	        // 这里推荐用 classpath 路径，而不是硬编码绝对路径
    	        String jrxml = "D:/KoueiSoft/cms_server/src/main/resources/tree.jrxml";
    	        JasperReport jasperReport = JasperCompileManager.compileReport(jrxml);

    	        // 2. 调用 service，取出员工数据（List<Map>）
    	        List<Map<String, ?>> employees = service.printInfo();

    	        // 3. 把 List<Map> 转成 Jasper 数据源
    	        JRDataSource ds = new JRMapCollectionDataSource(employees);

    	        // 4. 填充报表（这里没有额外参数，就传一个空 map）
    	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), ds);

    	        // 5. 输出 PDF 到本地
    	        String outputFile = "D:/KoueiSoft/cms_server/output/employees.pdf";
    	        JasperExportManager.exportReportToPdfFile(jasperPrint, outputFile);

    	        System.out.println("报表已生成: " + outputFile);

    	    } catch (Exception e) {
    	        e.printStackTrace();
    	        throw new RuntimeException("生成报表失败", e);
    	    }
    }

}
