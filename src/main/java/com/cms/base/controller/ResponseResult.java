package com.cms.base.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseResult<T> {
    private boolean status;
    private String message;
    private T data;
    private Integer total; // 可选：分页用
}