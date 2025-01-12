package com.cms.module.login;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.common.util.JwtUtil;

@RestController
//@CrossOrigin(origins = "http://localhost:8081") // 允许从指定域访问
@RequestMapping("/auth")
public class LoginController {

    /**
     * ログインロジック
     * 
     * @param loginData 
     * @return Token情報
     */
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> loginData) {
    	
        String username = loginData.get("username");
        String password = loginData.get("password");

        // 模拟用户认证（这里可以连接数据库验证）
        if ("admin".equals(username) && "password".equals(password)) {
        	
            String token = JwtUtil.generateToken(username);//Token作成

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("expiresIn", 3600); // 单位：秒
            
            return response;
           
        } else {
            throw new RuntimeException("ユーザとパスワードが不正です。");
        }
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
