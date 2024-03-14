package com.xht.kettle.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.kettle.entity.KettleDemo;
import com.xht.kettle.entity.KettleFile;
import com.xht.kettle.entity.KettleReqBody;
import com.xht.kettle.entity.Student;
import com.xht.kettle.mapper.KettleFileMapper;
import com.xht.kettle.service.KettleFileService;
import com.xht.kettle.service.KettleService;
import com.xht.kettle.utils.ZipFileUtils;
import com.xht.kettle.vo.R;
import com.xht.kettle.vo.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.pentaho.di.core.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/kettle")
@Slf4j
public class KettleController {

    @Resource
    private KettleService kettleService;

    @Value("${kettleConfig.filepath}")
    private String savaPath;

    @Autowired
    private KettleFileService kettleFileService;

    @Autowired
    private KettleFileMapper kettleFileMapper;


    @GetMapping("/execKtr")
    @Operation(summary = "执行Ktr")
    public R runKtr(String filename) {
        long l1 = System.currentTimeMillis();
        boolean b = kettleService.runTaskKtr(filename, null);
        long l2 = System.currentTimeMillis();
        log.info("时间:"+ (l2 - l1));
        return R.build( null, b?ResultCodeEnum.SUCCESS:ResultCodeEnum.FAILED);
    }

    @GetMapping("/execKjb")
    @Operation(summary = "执行Kjb")
    public R runKjb(String filename) {
        long l1 = System.currentTimeMillis();
        boolean b = kettleService.runTaskKjb(filename, null);
        long l2 = System.currentTimeMillis();
        log.info("时间:"+ (l2 - l1));
        return R.build( null, b?ResultCodeEnum.SUCCESS:ResultCodeEnum.FAILED);
    }

    @GetMapping("/getJson")
    @Operation(summary = "getJson")
    public R getJson() {
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
    public R getJsonByPost(@RequestBody KettleReqBody kettleReqBody) {
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
    public R getStudents() {
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
        KettleFile kettleFile = new KettleFile();
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
            String fileExtension = originalFilename.substring(originalFilename.indexOf("."));
            //判断文件类型
            if (!fileExtension.equalsIgnoreCase(".ktr") && !fileExtension.equalsIgnoreCase(".kjb")){
                throw new Exception("文件类型不符合");
            }

            String fullPath = generateUniqueFileName(directory.getPath(), originalFilename);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(Files.newOutputStream(new File(fullPath).toPath()));
            bufferedOutputStream.write(file.getBytes());
            bufferedOutputStream.close();

            //相关信息保存到数据库
            String filename = StringUtils.stripFilenameExtension(originalFilename);
            kettleFile.setTaskName(filename);
            kettleFile.setFilePath(fullPath);
            kettleFile.setCreator("test");

            kettleFile.setType(fileExtension);
            kettleFileService.save(kettleFile);
        }catch (Exception e){
            e.printStackTrace();
            return R.build(kettleFile,ResultCodeEnum.FAILED);
        }
        return R.build(kettleFile,ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/save")
    @Operation(summary = "新增")
    public R save(@RequestBody KettleFile kettleFile){
        kettleFileService.saveOrUpdate(kettleFile);
        return R.build(ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/download/{id}")
    @Operation(summary = "下载文件")
    public ResponseEntity<UrlResource> download(@PathVariable("id") Integer fileId){
        try {

            KettleFile kettleFile = kettleFileService.getById(fileId);
            //本地路径地址转为url编码路径
            URI urlPath= Paths.get(kettleFile.getFilePath()).toUri();
            UrlResource resource = new UrlResource(urlPath);
            if (resource.exists()){
                // Determine the file's content type
                String contentType = "application/octet-stream"; // Default to binary file if type is unknown

                // Set the response headers
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + kettleFile.getTaskName()+kettleFile.getType() + "\"")
                        .body(resource);
            }else {
                return ResponseEntity.notFound().build();
            }


        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/download/batch")
    @Operation(summary = "批量下载文件")
    public ResponseEntity<ByteArrayResource> downloadBatch(@RequestBody ArrayList<Integer> idList){
        List<File> fileList = new ArrayList<>();

        for (Integer id : idList) {
            KettleFile byId = kettleFileService.getById(id);
            if (byId != null){
                fileList.add(new File(byId.getFilePath()));
            }
        }

        try {

            // 创建一个临时zip文件
            File zipFile = File.createTempFile(UUID.randomUUID().toString().substring(0,10), ".zip");

            // 创建ZipOutputStream
            FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zipOut = new ZipOutputStream(fos);

            // 将文件写入zip文件
            for (File file : fileList) {
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

            // 将zip文件转换为字节数组
            byte[] zipBytes = FileCopyUtils.copyToByteArray(zipFile);

            // 设置响应头
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+zipFile.getName());

            // 返回zip文件给前端进行下载
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new ByteArrayResource(zipBytes));

        } catch (Exception e) {
            throw new RuntimeException(e);
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

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除")
    @Transactional
    public R delete(@PathVariable("id") Integer id){
        KettleFile kettleFile = kettleFileService.getById(id);
        if (kettleFile != null){
            //删除数据库
            kettleFileService.removeById(id);
            //删除本地文件
            String filePath = kettleFile.getFilePath();
            File file = new File(filePath);
            if (file.exists()){
                if (file.isFile()){
                    file.delete();
                }else {
                    deleteDirectory(filePath);
                }
            }
        }
        return R.build(ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/delete/batch")
    @Operation(summary = "批量删除")
    @Transactional
    public R delete(@RequestBody ArrayList<Integer> idList){
        for (Integer id : idList) {
            KettleFile kettleFile = kettleFileService.getById(id);
            if (kettleFile != null){
                //删除数据库
                kettleFileService.removeById(id);
                //删除本地文件
                String filePath = kettleFile.getFilePath();
                File file = new File(filePath);
                if (file.exists()){
                    if (file.isFile()){
                        file.delete();
                    }else {
                        deleteDirectory(filePath);
                    }
                }
            }
        }

        return R.build(ResultCodeEnum.SUCCESS);
    }



    /**
     * 删除目录（文件夹）以及目录下的文件
     * @param   sPath 被删除目录的文件路径
     * @return  目录删除成功返回true，否则返回false
     */
    public  boolean deleteDirectory(String sPath) {
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        if (files!=null){
            for (File file : files) {
                //删除子文件
                if (file.isFile()) {
                    flag = file.delete();
                } //删除子目录
                else {
                    flag = deleteDirectory(file.getAbsolutePath());
                }
                if (!flag) break;
            }
        }

        if (!flag) return false;
        //删除当前目录
        return dirFile.delete();
    }


    @GetMapping("/kettleFile")
    @Operation(summary = "获取文件列表")
    public R getKettleFile(@RequestParam(value = "taskName",required = false) String taskName,
                           @RequestParam("current") Long current,
                           @RequestParam("pageSize") Long pageSize){
        Page<KettleFile> kettleFilePage =  new Page<>();

        if (StringUtils.hasLength(taskName)){
            LambdaQueryWrapper<KettleFile> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(KettleFile::getTaskName,taskName);
            kettleFilePage = kettleFileMapper.selectPage(new Page<>(current, pageSize), queryWrapper);

        }else {
            LambdaQueryWrapper<KettleFile> queryWrapper = new LambdaQueryWrapper<>();
            kettleFilePage = kettleFileMapper.selectPage(new Page<>(current, pageSize), queryWrapper);

        }
        return R.build(kettleFilePage,ResultCodeEnum.SUCCESS);
    }
}
