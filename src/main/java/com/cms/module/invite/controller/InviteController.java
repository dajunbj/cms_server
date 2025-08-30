package com.cms.module.invite.controller;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.base.controller.ResultUtils;
import com.cms.module.invite.service.InviteService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.mail.internet.MimeMessage;

@RestController
@RequestMapping("/invite")
public class InviteController {

    @Autowired
    InviteService service;
    
    private final JavaMailSender mailSender;
    
    @Autowired
    public InviteController(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Value("${mail.invite.subject}")
    private String subject;

    @Value("${mail.invite.body}")
    private String bodyTemplate;

    @PostMapping("/send")
    public ResponseEntity<?> register(@RequestBody Map<String, Object> BasicInfo) {
    	String dobString = (String) BasicInfo.get("date_of_birth");
    	Instant instant = Instant.parse(dobString);
    	LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
    	java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
    	BasicInfo.put("date_of_birth", sqlDate);
    	Date now = new Date();
    	Date expiryDate = new Date(now.getTime() + 3600 * 1000); // 当前时间加1小时
    	String otpPwd = UUID.randomUUID().toString();
    	String userMail = (String) BasicInfo.get("email");
    	BasicInfo.put("pwd", otpPwd);
    	
    	//验证阶段的token加密秘钥，正式阶段需要额外做加密存储
    	String secret = "4r8zKFJ!o2@ud3929vbg3RUjflsl29wl";
    	
    	
    	//Token秘钥加密化
    	Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        try {
        	
        	//保存登录信息
            service.registData(BasicInfo);
            
            //生成URL中的Token
            String token = Jwts.builder()
            		.setSubject("invite")
            		.claim("mail", userMail)
            		.claim("pwd", otpPwd)
            	    .setIssuedAt(now)
            	    .setExpiration(expiryDate)
            	    .signWith(key, SignatureAlgorithm.HS256)
            	    .compact();
            
            //拼接Url（正式环境需要进行修改）
            String inviteUrl = "localhost:8081/regist/loginRegister?token=" + token;
            
            //对邮件正文进行拼接，调用yml邮件文模板
            String body = bodyTemplate
                    .replace("{{username}}", (String)BasicInfo.get("name"))
                    .replace("{{system}}", "精算支援システム")
                    .replace("{{loginId}}",(String)BasicInfo.get("login_id"))
                    .replace("{{otpPwd}}",otpPwd)
                    .replace("{{link}}", inviteUrl);

            //邮件发送
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
            helper.setTo(userMail);
            helper.setSubject(subject);
            helper.setText(body, false); // false表示纯文本，如果要支持HTML，则传true
            mailSender.send(message);

            return ResultUtils.success("発送成功");
        } catch (Exception e) {
            return ResultUtils.serverError("発送失敗: " + e.getMessage());
        }
    }
    
    

}
