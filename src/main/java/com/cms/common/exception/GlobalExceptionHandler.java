package com.cms.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cms.common.util.ResponseResult;

/**
 * 全体例外処理クラス。
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * カスタムビジネス例外を処理
     * @param ex CustomBusinessException
     * @return 標準レスポンス
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseResult<Void> handleBusinessException(BusinessException ex) {
        logger.error("ビジネス例外: {}", ex.getMessage());
        return new ResponseResult<>(ex.getCode(), ex.getMessage(), null);
    }

    /**
     * 一般的な例外を処理
     * @param ex Exception
     * @return 標準レスポンス
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult<Void> handleException(Exception ex) {
        logger.error("システム例外: ", ex);
        return new ResponseResult<>("500", "内部サーバエラーが発生しました", null);
    }
}
