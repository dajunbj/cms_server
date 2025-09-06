package com.cms.module.login.controller;

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
import com.cms.module.login.service.LoginService;
import com.warrenstrange.googleauth.GoogleAuthenticator;

import jakarta.servlet.http.HttpSession;

@RestController
//@CrossOrigin(origins = "http://localhost:8081") // 允许从指定域访问
@RequestMapping("/auth")
public class LoginController extends BaseController{

	@Autowired
	private LoginService service;
	
    /**
     * HASH
     * 
     * @param loginData 
     * @return Token情報
     */
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	
	
    /**
     * ログインロジック
     * 
     * @param loginData 
     * @return Token情報
     */
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, Object> loginData,HttpSession session) {
       
    	Map<String, Object> response = new HashMap<>();
    	String user = (String) loginData.get("username");
    	String password = (String) loginData.get("password");  
    	Employees ct = service.getLoginInfo(user);
    	String pwdHash = ct.getPassword_hash();  
    	GoogleAuthenticator gAuth = new GoogleAuthenticator();
    	boolean isValid = gAuth.authorize(ct.getAuth_code(), Integer.parseInt((String)loginData.get("authCode")));
    	try {
	    	if(isValid) {
		        if (passwordEncoder.matches(password,pwdHash)) {
		
		            //ログイン情報をセッションに保存
		            setLoginInfo(session, "userinfo", ct);
		            
		            //Token作成
		            String token = JwtUtil.generateToken((String)loginData.get("username"));
		        	response.put("success", true);
		            response.put("token", token);
		            response.put("expiresIn", 3600); // 单位：秒
		        	response.put("message", "");
		        	
		        	//契約プランを設定する
		        	Map<String, String> companyMap = new HashMap<>();
		        	companyMap.put("plan_code", ct.getPlan_code());
		        	companyMap.put("user_role", ct.getUser_role());
		        	response.put("company", companyMap);
		        	
		        } else {
		        	response.put("success", false);
		        	response.put("message", "ユーザとパスワードが正しくないです。");
		        }
	        }
	    	else {
	        	response.put("success", false);
	        	response.put("message", "一時認証コードが間違えています。");
	    	}
    	}
    	catch(Exception e) {
    		throw new RuntimeException("致命的なエラーが発生しました、管理者と連絡してください。");
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
