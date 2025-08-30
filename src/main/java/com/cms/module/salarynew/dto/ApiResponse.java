package com.cms.module.salarynew.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data @AllArgsConstructor @NoArgsConstructor
public class ApiResponse<T> {
  private int code; // 0=ok
  private String msg;
  private T data;
  public static <T> ApiResponse<T> ok(T d){ return new ApiResponse<>(0,"ok",d); }
  public static <T> ApiResponse<T> fail(String m){ return new ApiResponse<>(-1,m,null); }
}