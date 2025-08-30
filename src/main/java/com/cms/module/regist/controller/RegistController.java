package com.cms.module.regist.controller;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.base.controller.BaseController;
import com.cms.common.util.JwtUtil;
import com.cms.module.employee.entity.Employees;
import com.cms.module.regist.service.RegistService;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpSession;

@RestController
//@CrossOrigin(origins = "http://localhost:8081") // 允许从指定域访问
@RequestMapping("/regist")
public class RegistController extends BaseController{

	@Autowired
	private RegistService service;
	
    /**
     * HASH
     * 
     * @param loginData 
     * @return Token情報
     */
	
	//开发环境的token秘钥（正式环境必须采用更加保密的方式！）
	private final Key key = Keys.hmacShaKeyFor("4r8zKFJ!o2@ud3929vbg3RUjflsl29wl".getBytes());

	
	
    /**
     * ログインロジック
     * 
     * @param loginData 
     * @return Token情報
     */
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, Object> loginData,HttpSession session) {
    	
    	String user = (String) loginData.get("login_id");
    	String password = (String) loginData.get("pwd");  
    	String token = (String)loginData.get("token");
    	Employees ct = service.getLoginInfo(user);
        Map<String, Object> response = new HashMap<>();
        
        try {
        	
        	//回传token验证
        	Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token);
        	
        	//登录逻辑
	        if (password.equals(ct.getPwd())) {
	        	response.put("success", true);
	        	response.put("id",ct.getEmployee_id());
	        	response.put("message", "");
	        } else {
	        	response.put("success", false);
	        	response.put("message", "ユーザとパスワードが正しくないです。");
	        }
	        
	    //当token过期时
        }catch(ExpiredJwtException e) {
        	response.put("success", false);
        	response.put("message", "招待は時間切れました。管理者と連絡してください。");
        	return response;
        	
        //当token不正时	
        }catch(JwtException e) {
        	response.put("success", false);
        	response.put("message", "招待URLは不正です、確認してください。");
        	return response;
        }
        
        return response;
    }
    
    /**
     * ログイン情報更新ロジック
     * 
     * @param registData 
     * @return authenticatorUrl
     */
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String, Object> loginData) {
    	//调用谷歌验证方法
    	GoogleAuthenticator gAuth = new GoogleAuthenticator();
    	//创建随机秘钥(是否有检查秘钥是否重复的必要性？)
    	GoogleAuthenticatorKey key = gAuth.createCredentials();
    	//设定为GA验证创建秘钥
    	String secret = key.getKey();
    	
    	//对输入的密码进行乱码化
    	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    	String pwd = encoder.encode((String) loginData.get("password"));
    	
    	//根据登录信息获取情报
    	Map<String, Object> conditions = new HashMap<>();
    	conditions.put("login_id", (String) loginData.get("login_id"));
    	conditions.put("pwd", pwd);
    	conditions.put("employee_id", (String)loginData.get("employee_id"));
    	conditions.put("auth_code", secret);
    	Employees ct = service.getLoginInfo((String)loginData.get("login_id"));
    	String email = ct.getEmail();
    	Map<String, Object> response = new HashMap<>();
        String issuer = "共栄ソフト";
        
        //必要情报更新并生成OTP绑定连接，进行回传
        try {
        	service.updateLoginInfo(conditions);
        	String otpAuthUrl = String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s&digits=6", issuer , email , secret ,issuer);
        	response.put("success", true);
        	response.put("otpAuthUrl",otpAuthUrl);
        	response.put("message", "情報登録成功しました。");
        	
        }catch(Exception e){
        	response.put("success", false);
        	response.put("message", "致命的なエラーが発生しました、管理者と連絡してください。");
        	return response;
        }
        
        return response;
    }

    /**
     * Token検証
     * 
     * @param token ログイントークン 
     * @return Token認証結果
     */
    @GetMapping("/verify")
    public Map<String, Object> verifyToken(@RequestHeader("Authorization") String token) {
    	
        Map<String, Object> response = new HashMap<>();
        try {
            var claims = JwtUtil.validateToken(token);//Token検証
            
            response.put("username", claims.getSubject());
            response.put("message", "Token有効");
            
        } catch (Exception e) {
            throw new RuntimeException("Token無効");
        }
        return response;
    }
}
