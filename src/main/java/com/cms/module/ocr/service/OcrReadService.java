package com.cms.module.ocr.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

/**
 * ================================================================
 * OCR 読み取り・保存・更新・削除・エクスポート サービス（インターフェース）
 *
 * フロント要件に対応した機能を提供する。
 *  - OCR 読み取り（単体／バッチの単体処理に相当）
 *  - 保存（新規：画像＋項目／バッチ：配列）
 *  - 更新（項目のみ、画像は再送不要）
 *  - 削除（保存取消）
 *  - Excel 出力（ユーザー別）
 *
 * 例外方針：
 *  - ハードエラー（I/O、外部API 失敗など）は Exception をスロー
 *  - 呼び出し側（コントローラ）が HTTP ステータスを適切に返却
 * ================================================================
 */
public interface OcrReadService {

    // ------------------------------------------------------------
    // 1) OCR 読み取り
    // ------------------------------------------------------------

    /**
     * 画像1枚を OCR 解析する。
     *
     * 戻り Map の想定キー（フロント側と合意）：
     *   - issuer     : String 発行先（会社名／店舗名等）
     *   - number     : String 登録番号（任意、例：T＋13桁）
     *   - date       : String 発行日（yyyy-MM-dd に正規化した文字列推奨）
     *   - amount     : String 金額（例：3,740 等の表示用文字列）
     *   - full_text  : String OCR全文
     *
     * @param file OCR 対象画像
     * @return 解析結果のキー値マップ
     * @throws Exception OCR基盤エラー等
     */
    Map<String, Object> parse(MultipartFile file) throws Exception;

    // ------------------------------------------------------------
    // 2) 保存（新規／バッチ）
    // ------------------------------------------------------------

    /**
     * 1件を新規保存する（画像＋項目）。
     *
     * 保存後の状態は「確認済」を基本とし、DB の自動採番 ID を返す。
     *
     * @param issuer    発行先
     * @param number    登録番号（任意）
     * @param date      発行日（文字列）
     * @param amount    金額（文字列）
     * @param fullText  OCR全文（任意）
     * @param file      画像ファイル
     * @param userId    登録ユーザーID（文字列）
     * @return 生成されたレコードID
     * @throws Exception 画像保存／DB書込失敗 等
     */
    Long saveOne(String issuer, String number, String date, String amount,
                 String fullText, MultipartFile file, String userId) throws Exception;

    /**
     * 複数件を一括保存する（配列で同順位対応）。
     * 保存失敗した要素には null を設定して返す（フロントが index で突合）。
     *
     * @param files     画像群
     * @param issuer    各行の発行先
     * @param number    各行の登録番号（任意）
     * @param date      各行の発行日（文字列）
     * @param amount    各行の金額（文字列）
     * @param fullText  各行のOCR全文（任意）
     * @param userId    ユーザーID（文字列）
     * @return 各行に対応した保存ID（失敗は null）
     * @throws Exception 途中失敗時も処理を継続し得るが、致命時は例外
     */
    List<Long> saveBatch(List<MultipartFile> files, List<String> issuer, List<String> number,
                         List<String> date, List<String> amount, List<String> fullText,
                         String userId) throws Exception;

    // ------------------------------------------------------------
    // 3) 更新（項目のみ）
    // ------------------------------------------------------------

    /**
     * 既存1件を更新する（画像は再送不要、項目のみ上書き）。
     * 業務条件により、更新対象の所有者チェック等は実装側で行う。
     *
     * @param id      更新対象ID
     * @param issuer  発行先
     * @param number  登録番号（任意）
     * @param date    発行日（文字列）
     * @param amount  金額（文字列）
     * @param userId  操作ユーザーID（文字列）
     * @return 更新件数（通常 1）
     * @throws Exception 入力値不正／権限／DB 例外 等
     */
    int updateOne(Long id, String issuer, String number, String date, String amount, String userId) throws Exception;

    // ------------------------------------------------------------
    // 4) 削除（保存取消）
    // ------------------------------------------------------------

    /**
     * 既存1件を削除する（保存取消。画像ファイルの扱いは実装側で方針決定）。
     * 実装例：論理削除／物理削除／画像ファイルの削除可否 等。
     *
     * @param id     削除対象ID
     * @param userId 操作ユーザーID（文字列）
     * @return 削除件数（通常 1／存在なしは 0）
     * @throws Exception 権限／DB 例外 等
     */
    int deleteOne(Long id, String userId) throws Exception;

    // ------------------------------------------------------------
    // 5) エクスポート
    // ------------------------------------------------------------

    /**
     * Excel（.xlsx）をバイト配列で返す。
     * Content-Type：application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
     *
     * @param userId 出力対象ユーザーID
     * @return Excel バイト列
     * @throws Exception 生成時の I/O 例外 等
     */
    byte[] exportExcel(String userId) throws Exception;
}
