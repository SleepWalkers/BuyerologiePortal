package com.buyerologie.utils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.log4j.Logger;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtils {

    private static final Logger logger                   = Logger.getLogger(FileUploadUtils.class);

    private static final String OS                       = System.getProperty("os.name");

    private static final String UPPER_PATH               = "../";

    private static final String ROOT_PATH                = "buyerology";

    private static final String AVATAR_URI               = "/avatar";

    private static final String DEFALUT_LINUX_IMAGE_ROOT = ROOT_PATH + AVATAR_URI;

    private static String getRootPath(int deepth) {
        StringBuilder stringBuilder = new StringBuilder();
        if (OS.contains("Windows")) {
            deepth--;
        }
        for (int i = 0; i < deepth; i++) {
            stringBuilder.append(UPPER_PATH);
        }
        return stringBuilder.toString();
    }

    private static String getImageFileRootPath() {
        String path;
        File file = null;
        try {
            path = ClassLoaderUtil.getExtendResource("/").getPath();
            int pathSplitLen = path.split("/").length;

            String rootPath = ClassLoaderUtil.getExtendResource(getRootPath(pathSplitLen - 1))
                .getPath();

            file = new File(rootPath + DEFALUT_LINUX_IMAGE_ROOT);
        } catch (MalformedURLException e) {
            logger.error("", e);
        }
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getPath();
    }

    public static String uploadImage(MultipartFile multipartFile) {
        byte[] fileBytes = null;
        try {
            fileBytes = multipartFile.getBytes();
        } catch (IOException e) {
            logger.error("", e);
        }

        String fileName = UUIDUtils.getUUID() + multipartFile.getOriginalFilename();
        String filePath = getImageFileRootPath() + "/" + fileName;

        logger.info("文件路径为: " + filePath);
        File file = new File(filePath);
        file.setWritable(true, false);
        try {
            FileCopyUtils.copy(fileBytes, file);
        } catch (IOException e) {
            logger.error("", e);
        }
        return getImagePath(fileName);
    }

    private static String getImagePath(String fileName) {
        return AVATAR_URI + "/" + fileName;
    }
}
