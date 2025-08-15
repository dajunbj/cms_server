package com.cms.module.ocr.util;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.web.multipart.MultipartFile;

public class FileStorageUtil {
    private static final String SAVE_DIR = "uploads/receipts/";

    public static String saveImage(MultipartFile file) throws IOException {
        String basePath = System.getProperty("user.dir") + File.separator + "src/main/resources/static/" + SAVE_DIR;
        File dir = new File(basePath);
        if (!dir.exists()) dir.mkdirs();

        String filename = generateUniqueFilename(file.getOriginalFilename());
        File dest = new File(dir, filename);
        file.transferTo(dest);

        return "/static/" + SAVE_DIR + filename;  // 返回前端可访问路径
    }

    private static String generateUniqueFilename(String original) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String ext = original.substring(original.lastIndexOf('.') + 1);
        return "receipt_" + timestamp + "." + ext;
    }
}
