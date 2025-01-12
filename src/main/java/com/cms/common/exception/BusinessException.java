package com.cms.common.exception;

/**
 * ビジネスロジック用例外クラス。
 * 業務に特化した例外処理を実装。
 */
public class BusinessException extends BaseException {

    /**
     * パラメータなしの例外
     * @param string エラーコード
     * @param message エラーメッセージ
     */
    public BusinessException(String string, String message) {
        super(string, message);
    }

    /**
     * パラメータありの例外
     * @param code エラーコード
     * @param message フォーマット可能なメッセージ（例: "データ[%s]が存在しません"）
     * @param args メッセージに埋め込むパラメータ
     */
    public BusinessException(String code, String message, Object... args) {
        super(code, message, args);
    }
}
