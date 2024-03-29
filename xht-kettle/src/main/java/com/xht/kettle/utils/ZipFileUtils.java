package com.xht.kettle.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipFileUtils {

    /**
     * web下载打成压缩包的文件--流方式
     *
     * @param response 响应
     * @param fileList 文件列表
     * @param zipName  压缩包名
     */
    public static void downloadZipFiles(HttpServletResponse response, List<String> fileList, String zipName) {
        ZipOutputStream zipOutputStream = null;
        try {
            //设置响应头
            response.reset();
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("utf-8");
            //设置文件名称
            response.setHeader("Content-Disposition", "attachment;filename=" + zipName);
            zipOutputStream = new ZipOutputStream(response.getOutputStream());
            for (String file : fileList) {
                toZip(zipOutputStream, new File(file));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            //关闭资源
            if (null != zipOutputStream) {
                try {
                    zipOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 压缩文件
     *
     * @param zipOutputStream 压缩文件流
     * @param file            待压缩文件
     */
    public static void toZip(ZipOutputStream zipOutputStream, File file) {
        String filename = file.getName();
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(Files.newInputStream(file.toPath()));
            //设置压缩包内文件的名称
            zipOutputStream.putNextEntry(new ZipEntry(filename));
            int size;
            byte[] buffer = new byte[4096];
            while ((size = bis.read(buffer)) > 0) {
                zipOutputStream.write(buffer, 0, size);
            }
            zipOutputStream.closeEntry();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            //关闭资源
            if (null != bis) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 压缩文件
     */

    public static File fileListToZip(List<String> filePathList) throws Exception{

        File zipFile = File.createTempFile(UUID.randomUUID().toString().substring(10), ".zip");
        // 创建ZipOutputStream
        FileOutputStream fos = new FileOutputStream(zipFile);

        ZipOutputStream zipOut = new ZipOutputStream(fos);
        for (String filePath : filePathList) {
            File file = new File(filePath);
            String fileName = file.getName();
            //读取文件
            FileInputStream fis = new FileInputStream(file);
            ZipEntry zipEntry = new ZipEntry(file.getName());
            zipOut.putNextEntry(zipEntry);

            // 将文件内容写入zip文件
            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }

            fis.close();


        }
        // 关闭ZipOutputStream
        zipOut.close();
        fos.close();
        return zipFile;
    }


    private static void addToZipFile(String filePath, FileInputStream fis, ZipOutputStream zipOut)
            throws IOException {
        ZipEntry zipEntry = new ZipEntry(filePath);
        zipOut.putNextEntry(zipEntry);

        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }

        zipOut.closeEntry();
        fis.close();
    }
}