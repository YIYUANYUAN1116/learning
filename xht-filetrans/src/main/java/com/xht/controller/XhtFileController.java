package com.xht.controller;


import com.xht.model.params.CreateDocxParam;
import com.xht.model.params.PdfMergeParam;
import com.xht.model.params.WordToPdfParam;
import com.xht.service.XhtFileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/file")
public class XhtFileController {

    @Autowired
    private XhtFileService xhtFileService;

    @PostMapping("/docxToPdf")
    public ResponseEntity<byte[]> docxToPDF(@RequestBody WordToPdfParam wordToPdfParam){
        return  xhtFileService.docxToPDF(wordToPdfParam);
    }

    @PostMapping("/pdfMerge")
    public ResponseEntity<byte[]> pdfMerge(@RequestBody PdfMergeParam pdfMergeParam){
        return  xhtFileService.mergePdf(pdfMergeParam);
    }


    @PostMapping("/createDocx")
    public ResponseEntity<byte[]> createDocx(@RequestBody CreateDocxParam createDocxParam){
        return  xhtFileService.createDocx(createDocxParam);
    }
}
