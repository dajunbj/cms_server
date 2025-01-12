//package com.cms.common.util;
//
//import java.util.Date;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//
//public class JwtUtil {
//    private static final String SECRET_KEY = "1f0b5fd51da1439fdcf1c44b697e33d685df44443bc1aaefd278161c99b5d5ac";
//    private static final long EXPIRATION_TIME = 600000; // 10分钟
//
//    // 生成 Token
//    public static String generateToken(String username) {
//        return Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
//                .compact();
//    }
//
//    // 验证 Token
//    public static Claims validateToken(String token) {
//        return Jwts.parser()
//                .setSigningKey(SECRET_KEY)
//                .parseClaimsJws(token)
//                .getBody();
//    }
//}
package com.cms.common.util;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * JWTユーティリティクラス
 * トークンの生成および検証を行うためのメソッドを提供
 */
public class JwtUtil {
    // シークレットキー（トークンの署名用）
    private static final String SECRET_KEY = "1f0b5fd51da1439fdcf1c44b697e33d685df44443bc1aaefd278161c99b5d5ac";

    // トークンの有効期限（ミリ秒単位、ここでは10分）
    private static final long EXPIRATION_TIME = 6000000; // 10分

    /**
     * トークンを生成するメソッド
     * @param username ユーザー名
     * @return 生成されたJWTトークン
     */
    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // トークンの対象（ここではユーザー名）
                .setIssuedAt(new Date()) // トークンの発行時刻
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // トークンの有効期限
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // トークンの署名アルゴリズムとシークレットキー
                .compact(); // トークンのコンパクト表現を生成
    }

    /**
     * トークンを検証し、ペイロード情報を取得するメソッド
     * @param token 検証対象のJWTトークン
     * @return トークンから抽出されたクレーム（ペイロード情報）
     */
    public static Claims validateToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY) // トークンを検証する際のシークレットキー
                .parseClaimsJws(token) // トークンを解析
                .getBody(); // ペイロード情報（クレーム）を取得
    }
}
