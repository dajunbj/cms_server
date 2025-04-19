package com.cms.base.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

public class ResultUtils {

    public static <T> ResponseEntity<ResponseResult<T>> success(T data) {
        return ResponseEntity.ok(new ResponseResult<>(true, null, data, null));
    }

    public static <T> ResponseEntity<ResponseResult<List<T>>> success(List<T> data, int total) {
        return ResponseEntity.ok(new ResponseResult<>(true, null, data, total));
    }

    public static ResponseEntity<ResponseResult<Object>> success(String message) {
        return ResponseEntity.ok(new ResponseResult<>(true, message, null, null));
    }

    public static ResponseEntity<ResponseResult<Object>> error(String message) {
        return ResponseEntity.badRequest().body(new ResponseResult<>(false, message, null, null));
    }

    public static ResponseEntity<ResponseResult<Object>> serverError(String message) {
        return ResponseEntity.status(500).body(new ResponseResult<>(false, message, null, null));
    }
}