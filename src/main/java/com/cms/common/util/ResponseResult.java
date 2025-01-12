package com.cms.common.util;

import lombok.Data;

@Data
public class ResponseResult<T> {
    private String code;
    private String message;
    private T data;

    public ResponseResult(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}