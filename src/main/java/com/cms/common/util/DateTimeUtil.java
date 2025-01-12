package com.cms.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * 日付と時間のフォーマットおよび変換を行うユーティリティクラス。
 * 日本でよく使われるフォーマットをサポート。
 */
public class DateTimeUtil {

    // 共通のフォーマット定義
    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd"; // デフォルトの日付フォーマット
    private static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";  // デフォルトの時間フォーマット
    private static final String DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss"; // デフォルトの日時フォーマット
    private static final String JAPANESE_DATE_PATTERN = "yyyy年MM月dd日"; // 日本語形式

    // DateTimeFormatter インスタンス
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN, Locale.JAPAN);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_PATTERN, Locale.JAPAN);
    private static final DateTimeFormatter JAPANESE_DATE_FORMATTER = DateTimeFormatter.ofPattern(JAPANESE_DATE_PATTERN, Locale.JAPAN);

    /**
     * LocalDate を文字列に変換 (デフォルトフォーマット)
     * @param date LocalDate オブジェクト
     * @return フォーマットされた日付文字列
     */
    public static String formatDate(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    /**
     * LocalDateTime を文字列に変換 (デフォルトフォーマット)
     * @param dateTime LocalDateTime オブジェクト
     * @return フォーマットされた日時文字列
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DATE_TIME_FORMATTER);
    }

    /**
     * 文字列を LocalDate に変換 (デフォルトフォーマット)
     * @param dateStr フォーマットされた日付文字列
     * @return LocalDate オブジェクト
     */
    public static LocalDate parseDate(String dateStr) {
        return LocalDate.parse(dateStr, DATE_FORMATTER);
    }

    /**
     * 文字列を LocalDateTime に変換 (デフォルトフォーマット)
     * @param dateTimeStr フォーマットされた日時文字列
     * @return LocalDateTime オブジェクト
     */
    public static LocalDateTime parseDateTime(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, DATE_TIME_FORMATTER);
    }

    /**
     * 日本語形式の日付文字列を LocalDate に変換
     * @param dateStr 日本語形式の日付文字列 (例: 2024年12月26日)
     * @return LocalDate オブジェクト
     */
    public static LocalDate parseJapaneseDate(String dateStr) {
        return LocalDate.parse(dateStr, JAPANESE_DATE_FORMATTER);
    }

    /**
     * 任意のフォーマットで日付文字列を LocalDate に変換
     * @param dateStr 日付文字列
     * @param pattern フォーマットパターン
     * @return LocalDate オブジェクト
     * @throws DateTimeParseException 無効なフォーマットの場合にスロー
     */
    public static LocalDate parseDateWithPattern(String dateStr, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.JAPAN);
        return LocalDate.parse(dateStr, formatter);
    }

    /**
     * 任意のフォーマットで日時文字列を LocalDateTime に変換
     * @param dateTimeStr 日時文字列
     * @param pattern フォーマットパターン
     * @return LocalDateTime オブジェクト
     * @throws DateTimeParseException 無効なフォーマットの場合にスロー
     */
    public static LocalDateTime parseDateTimeWithPattern(String dateTimeStr, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.JAPAN);
        return LocalDateTime.parse(dateTimeStr, formatter);
    }
}
