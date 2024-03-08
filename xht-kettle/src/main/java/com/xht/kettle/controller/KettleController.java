package com.xht.kettle.controller;


import com.xht.kettle.entity.KettleDemo;
import com.xht.kettle.entity.KettleReqBody;
import com.xht.kettle.entity.Student;
import com.xht.kettle.service.KettleService;
import com.xht.kettle.vo.R;
import com.xht.kettle.vo.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.pentaho.di.core.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("/kettle")
@Slf4j
public class KettleController {

    @Resource
    private KettleService kettleService;

    @Value("${kettleConfig.filepath}")
    private String savaPath;

    @GetMapping("/execKtr")
    @Operation(summary = "执行Ktr")
    private R runKtr(String filename) {
        long l1 = System.currentTimeMillis();
        boolean b = kettleService.runTaskKtr(filename, null);
        long l2 = System.currentTimeMillis();
        log.info("时间:"+ (l2 - l1));
        return R.build( null, b?ResultCodeEnum.SUCCESS:ResultCodeEnum.FAILED);
    }

    @GetMapping("/execKjb")
    @Operation(summary = "执行Kjb")
    private R runKjb(String filename) {
        long l1 = System.currentTimeMillis();
        boolean b = kettleService.runTaskKjb(filename, null);
        long l2 = System.currentTimeMillis();
        log.info("时间:"+ (l2 - l1));
        return R.build( null, b?ResultCodeEnum.SUCCESS:ResultCodeEnum.FAILED);
    }

    @GetMapping("/getJson")
    @Operation(summary = "getJson")
    private R getJson() {
        ArrayList<KettleDemo> kettleDemos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            KettleDemo kettleDemo = new KettleDemo();
            kettleDemo.setUsername(UUID.randomUUID().toString().substring(0,6));
            kettleDemos.add(kettleDemo);
        }
        return R.build(kettleDemos,ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/getJsonByPost")
    @Operation(summary = "getJsonByPost")
    private R getJsonByPost(@RequestBody KettleReqBody kettleReqBody) {
        int nums = kettleReqBody.getNums();
        ArrayList<KettleDemo> kettleDemos = new ArrayList<>();
        for (int i = 0; i < nums; i++) {
            KettleDemo kettleDemo = new KettleDemo();
            kettleDemo.setUsername(UUID.randomUUID().toString().substring(0,6));
            kettleDemos.add(kettleDemo);
        }
        return R.build(kettleDemos,ResultCodeEnum.SUCCESS);
    }


    @GetMapping("/getStudents")
    @Operation(summary = "getStudents")
    private R getStudents() {
        ArrayList<Student> kettleDemos = new ArrayList<>();
        Student student1 = new Student("1001", "张", "女", "语文", "80", "抽烟烫头");
        Student student2 = new Student("1001", "张", "女", "数学", "90", "抽烟烫头");
        Student student3 = new Student("1002", "李", "女", "语文", "81", "唱歌跳舞");
        Student student4 = new Student("1002", "李", "女", "数学", "82", "唱歌跳舞");
        kettleDemos.add(student1);
        kettleDemos.add(student2);
        kettleDemos.add(student3);
        kettleDemos.add(student4);

        return R.build(kettleDemos,ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/upload")
    public R uploadFile(@RequestParam("file") MultipartFile file){

        if (file == null){
            return R.build(null,999,"请选择一个文件上传");
        }
        try {

            File directory  = new File(savaPath);
            if (!directory.exists()){
                boolean mkdirs = directory.mkdirs();
                if (!mkdirs){
                    return R.build(null,999,"创建文件夹失败："+directory);
                }
            }

            String originalFilename = file.getOriginalFilename();

            String fullPath = generateUniqueFileName(directory.getPath(), originalFilename);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(Files.newOutputStream(new File(fullPath).toPath()));
            bufferedOutputStream.write(file.getBytes());
            bufferedOutputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return R.build(ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/download")
    @Operation(summary = "下载文件")
    public ResponseEntity<UrlResource> download(@RequestParam("filename") String filename){
        try {
            Path fileStorageLocation  = Paths.get(savaPath);
            Path filePath = fileStorageLocation.resolve(filename).normalize();
            UrlResource resource = new UrlResource(filePath.toUri());
            if (resource.exists()){
                // Determine the file's content type
                String contentType = "application/octet-stream"; // Default to binary file if type is unknown

                // Set the response headers
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            }else {
                return ResponseEntity.notFound().build();
            }


        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }

    }

    @GetMapping("/download2")
    @Operation(summary = "下载文件v2")
    public R download2(@RequestParam("filename") String filename, HttpServletResponse response){
        try {
            Path fileStorageLocation  = Paths.get(savaPath);
            Path filePath = fileStorageLocation.resolve(filename).normalize();
            File file = filePath.toFile();
            if (file.exists()){
                response.setContentType("application/octet-stream");
                String headerKey = "Content-Disposition";
                String headerValue = "attachment; filename=\"" + file.getName() + "\"";
                response.setHeader(headerKey, headerValue);
                response.setContentLength((int) file.length());

                BufferedInputStream inputStream = new BufferedInputStream(Files.newInputStream(file.toPath()));
                BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream());

                byte[] buffer = new byte[8192];
                int bytesRead = -1;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                inputStream.close();
                outputStream.close();
            }else {
                return R.build(null,999,"文件不存在");
            }
        }catch (Exception e){
            return R.build(null,999,"下载异常");
        }

        return R.build(ResultCodeEnum.SUCCESS);
    }

    /**
     * 处理同名文件的情况
     * @param storageDirectory
     * @param originalFileName
     * @return
     */
    public String generateUniqueFileName(String storageDirectory, String originalFileName) {
        String fileNameWithoutExtension = StringUtils.stripFilenameExtension(originalFileName);
        String fileExtension = StringUtils.getFilenameExtension(originalFileName);
        String fullPath = storageDirectory + File.separator + originalFileName;
        int fileCount = 0;

        // 检查文件是否存在，如果存在则追加序号
        while (Files.exists(Paths.get(fullPath))) {
            fileCount++;
            fullPath = storageDirectory + File.separator + fileNameWithoutExtension + "(" + fileCount + ")" +
                    (fileExtension != null ? "." + fileExtension : "");
        }

        // 返回独特的文件名路径
        return fullPath;
    }

}
