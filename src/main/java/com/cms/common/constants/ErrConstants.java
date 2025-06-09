package com.cms.common.constants;

//定义一个通用的常量类
public class ErrConstants {

 // 状态码
 public static final int STATUS_SUCCESS = 200;
 public static final int STATUS_ERROR = 500;

 // 默认值
 public static final String DEFAULT_USER_ROLE = "USER";
 public static final int MAX_LOGIN_ATTEMPTS = 3;

 // 嵌套类分组
 public static class Database {
     public static final String DB_URL = "jdbc:mysql://localhost:3306/mydb";
     public static final String DB_USER = "root";
     public static final String DB_PASSWORD = "password";
 }

 public static final String ERR_REGIST_SUCESS = "登録が成功した。";
 public static final String ERR_REGIST_FAILUE = "登録が失敗した。";
 public static final String ERR_HAVE_NO_RESULT = "検索結果は存在しません。";
}
