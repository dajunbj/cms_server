package com.cms.common.util;

/**
 * 日付と時間のフォーマットおよび変換を行うユーティリティクラス。
 * 日本でよく使われるフォーマットをサポート。
 */
public class StringUtils {

    /**
     * 文字列が null または 空文字（""）の場合に true を返す。
     *
     * @param val 対象文字列
     * @return 空またはnullならtrue、それ以外false
     */
    public static boolean isEmpty(String val) {
        return val == null || val.trim().isEmpty();
    }

    /**
     * 文字列が null でも 空文字（""）でもない場合に true を返す。
     *
     * @param val 対象文字列
     * @return 空でもnullでもなければtrue、それ以外false
     */
    public static boolean isNotEmpty(String val) {
        return !isEmpty(val);
    }

}
