package com.xht.service;


import com.xht.model.params.CreateDocxParam;
import com.xht.model.params.PdfMergeParam;
import com.xht.model.params.WordToPdfParam;
import org.springframework.http.ResponseEntity;

public interface XhtFileService {
    //docx 转换成 pdf
    ResponseEntity<byte[]> docxToPDF(WordToPdfParam wordToPdfParam);

    //合并pdf
    ResponseEntity<byte[]> mergePdf(PdfMergeParam pdfMergeParam);

    //模板创建docx
    ResponseEntity<byte[]> createDocx(CreateDocxParam createDocxParam);

}
