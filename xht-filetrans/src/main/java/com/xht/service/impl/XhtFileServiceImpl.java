package com.xht.service.impl;

import com.alibaba.fastjson.JSON;
import com.xht.model.params.CreateDocxParam;
import com.xht.model.params.PdfMergeParam;
import com.xht.model.params.WordToPdfParam;
import com.xht.service.XhtFileService;
import com.xht.utils.XhtFileOperationUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@Slf4j
@Service
public class XhtFileServiceImpl implements XhtFileService {
    public final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, " +
            "like Gecko) Chrome/75.0.3770.90 Safari/537.36";

    @Override
    public ResponseEntity<byte[]> docxToPDF(WordToPdfParam wordToPdfParam) {
        try {
            XhtFileOperationUtil.wordToPDF(wordToPdfParam.getWordFilePath(), wordToPdfParam.getPdfFilePath());
            //将转换好的pdf返回
            File pdfFile = new File(wordToPdfParam.getPdfFilePath());
            return buildResponse(pdfFile,MediaType.APPLICATION_PDF);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<byte[]> mergePdf(PdfMergeParam pdfMergeParam) {
        try {
            List<String> mergePaths = pdfMergeParam.getMergePaths();
            String targetPath = pdfMergeParam.getTargetPath();
            if (mergePaths.isEmpty() || !StringUtils.hasLength(targetPath)){
                log.error("合并文件路径为空 或者 目标路径为空");
                return null;
            }

            PDFMergerUtility mergerUtility = new PDFMergerUtility();
            for (String mergePath : mergePaths) {
                //如果是word，先转成pdf
                int lastIndexOf = mergePath.lastIndexOf(".");
                String substring = mergePath.substring(lastIndexOf);
                if (substring.equals(".docx")){
                    String tFilePath = mergePath.substring(0,lastIndexOf)+".pdf";
                    XhtFileOperationUtil.wordToPDF(mergePath,tFilePath);
                    mergerUtility.addSource(new File(tFilePath));
                }else if (substring.equals(".pdf")){
                    mergerUtility.addSource(new File(mergePath));
                }else {
                    //todo 报错
                }

            }
            mergerUtility.setDestinationFileName(targetPath);
            mergerUtility.mergeDocuments(null);

            return buildResponse(new File(pdfMergeParam.getTargetPath()),MediaType.APPLICATION_PDF);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<byte[]> createDocx(CreateDocxParam createDocxParam) {
        try {
            String dataClassName = createDocxParam.getDataClassName();
            Class<?> dataClass = Class.forName(dataClassName);
            Object o = JSON.parseObject(JSON.toJSONString(createDocxParam.getData()), dataClass);
            XhtFileOperationUtil.createDocxFile(new File(createDocxParam.getTempDocxPath()),new File(createDocxParam.getTargetDocxPath()),o,dataClass);
            return buildResponse(new File(createDocxParam.getTargetDocxPath()),MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private ResponseEntity<byte[]> buildResponse(File file, MediaType mediaType){
        byte[] pdfBytes = new byte[0];
        HttpHeaders headers = null;
        try {
            //将文件转换成字节数组返回
            pdfBytes = XhtFileOperationUtil.convertFileToByteArray(file);
            headers = new HttpHeaders();
            headers.setContentType(mediaType);
            headers.set("User-Agent",USER_AGENT);
            String encodedFileName = URLEncoder.encode(file.getName(), "UTF-8");
            headers.setContentDispositionFormData("attachment", encodedFileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }



}
