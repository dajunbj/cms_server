package com.cms.module.salarynew.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class ApiResp<T> {
  private int code;
  private String msg;
  private T data;
  public static <T> ApiResp<T> ok(T data){ return new ApiResp<>(0,"ok",data); }
  public static <T> ApiResp<T> fail(String msg){ return new ApiResp<>(-1,msg,null); }
}