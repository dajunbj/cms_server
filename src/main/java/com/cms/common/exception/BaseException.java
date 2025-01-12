package com.cms.common.exception;

/**
 * 共通基底例外クラス。
 * ビジネスロジックで発生する例外の基本クラス。
 */
public class BaseException extends RuntimeException {

    private final String code; // エラーコード
    private final String message; // エラーメッセージ

    /**
     * コンストラクタ（パラメータなし）
     * @param code エラーコード
     * @param message エラーメッセージ
     */
    public BaseException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    /**
     * コンストラクタ（パラメータあり）
     * @param code エラーコード
     * @param message フォーマット可能なメッセージ（例: "ユーザーID[%s]が見つかりません"）
     * @param args メッセージに埋め込むパラメータ
     */
    public BaseException(String code, String message, Object... args) {
        super(String.format(message, args));
        this.code = code;
        this.message = String.format(message, args);
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
