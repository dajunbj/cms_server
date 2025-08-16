package com.cms.module.ocr.util;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.web.multipart.MultipartFile;

/**
 * ファイル保存用ユーティリティクラス。
 *
 * 主に「領収書画像」をサーバーに保存するために使用する。
 * 保存先: src/main/resources/static/uploads/receipts/
 *
 * 利用シーン:
 * - 領収書アップロード画面でユーザーが画像をアップロードしたとき
 * - OCR処理実行時に画像とOCR結果を紐付けるために利用
 */
public class FileStorageUtil {

    /** 領収書画像の保存ディレクトリ（プロジェクト内の static/ 配下） */
    private static final String SAVE_DIR = "uploads/receipts/";

    /**
     * アップロードされた画像を保存し、フロントから参照可能なパスを返す。
     *
     * @param file MultipartFile（Springで受け取ったアップロードファイル）
     * @return フロントエンドからアクセス可能な画像URL（例: /static/uploads/receipts/receipt_20250116235959.png）
     * @throws IOException 保存処理中にエラーが発生した場合
     */
    public static String saveImage(MultipartFile file) throws IOException {
        // 保存先の基準パス：プロジェクト直下の static ディレクトリ
        String basePath = System.getProperty("user.dir")
                + File.separator + "src/main/resources/static/"
                + SAVE_DIR;

        File dir = new File(basePath);
        if (!dir.exists()) {
            dir.mkdirs(); // ディレクトリが存在しない場合は作成
        }

        // ファイル名を一意に生成（タイムスタンプ付き）
        String filename = generateUniqueFilename(file.getOriginalFilename());

        // 保存先ファイルオブジェクト
        File dest = new File(dir, filename);

        // 実際に保存
        file.transferTo(dest);

        // フロントから参照可能なパスを返却
        return "/static/" + SAVE_DIR + filename;
    }

    /**
     * 一意なファイル名を生成する。
     *
     * @param original 元のファイル名（拡張子を利用する）
     * @return 例: receipt_20250116235959.jpg
     */
    private static String generateUniqueFilename(String original) {
        // yyyyMMddHHmmss 形式のタイムスタンプ
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        // 拡張子（例: "png", "jpg"）
        String ext = original.substring(original.lastIndexOf('.') + 1);

        return "receipt_" + timestamp + "." + ext;
    }
}
