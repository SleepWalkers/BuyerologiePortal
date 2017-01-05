package com.buyerologie.utils;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtils {

    private static final Logger logger                   = Logger.getLogger(FileUploadUtils.class);

    private static final String ROOT_PATH                = "\\buyerology";
    private static final String DEFALUT_LINUX_IMAGE_ROOT = ROOT_PATH + "\\avatar\\";

    private static String getImageFileRootPath() {
        File file = new File(getDefalutImageRoot());
        if (!file.exists()) {
            file.mkdirs();
        }

        return file.getPath();
    }

    private static String getDefalutImageRoot() {
        return DEFALUT_LINUX_IMAGE_ROOT;
    }

    public static String uploadImage(MultipartFile multipartFile) {
        byte[] fileBytes = null;
        try {
            fileBytes = multipartFile.getBytes();
        } catch (IOException e) {
            logger.error("", e);
        }

        String filePath = getImageFileRootPath() + "\\" + UUIDUtils.getUUID()
                          + multipartFile.getOriginalFilename();

        File file = new File(filePath);
        try {
            FileCopyUtils.copy(fileBytes, file);
        } catch (IOException e) {
            logger.error("", e);
        }
        return filePath.replace(ROOT_PATH, "").replaceAll("\\\\", "/");
    }
}
