package com.cms.common.constants;

//定义一个通用的常量类
public class ErrConstants {
 // 基础配置
 public static final String ERR_001 = "MyApplication";
 public static final String VERSION = "1.0.0";

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
 
}
