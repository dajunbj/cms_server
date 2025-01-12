package com.cms.common.base;

import org.springframework.web.bind.annotation.RestController;

import com.cms.common.util.ResponseResult;

/**
 * 共通コントローラクラス。よく使うレスポンス処理をカプセル化。
 */
@RestController
public abstract class BaseController {

    /**
     * 成功レスポンス
     * @param data 返却データ
     * @param <T> データの型
     * @return 共通レスポンスオブジェクト
     */
    protected <T> ResponseResult<T> success(T data) {
        return new ResponseResult<>("200", "成功", data);
    }

    /**
     * 成功レスポンス（データなし）
     * @return 共通レスポンスオブジェクト
     */
    protected ResponseResult<Void> success() {
        return new ResponseResult<>("200", "成功", null);
    }

    /**
     * エラーレスポンス
     * @param message エラーメッセージ
     * @return 共通レスポンスオブジェクト
     */
    protected ResponseResult<Void> error(String message) {
        return new ResponseResult<>("500", message, null);
    }

    /**
     * エラーレスポンス（カスタムステータスコード）
     * @param code ステータスコード
     * @param message エラーメッセージ
     * @return 共通レスポンスオブジェクト
     */
    protected ResponseResult<Void> error(String code, String message) {
        return new ResponseResult<>(code, message, null);
    }
}
