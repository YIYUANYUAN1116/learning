package com.xht.html;

import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.jaxb.Context;
import org.docx4j.model.table.TblFactory;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;


public class WordAddWaterMark {
    public static void main(String[] args) {
        try {
//            // 创建WordprocessingMLPackage实例
//            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();
//
//            // 添加水印
//            addWatermark(wordMLPackage, "Confidential");
//
//            // 保存Word文档
//            wordMLPackage.save(new File("watermarked_output.docx"));
            test1();
            test2();
            test3();
            test4();
            System.out.println("Word文档生成成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addWatermark(WordprocessingMLPackage wordMLPackage, String watermarkText) {
        ObjectFactory factory = new ObjectFactory();
        SectPr sectPr = wordMLPackage.getMainDocumentPart().getJaxbElement().getBody().getSectPr();
        if (sectPr == null) {
            sectPr = factory.createSectPr();
            wordMLPackage.getMainDocumentPart().getJaxbElement().getBody().setSectPr(sectPr);
        }

        // 创建水印内容
        P watermark = factory.createP();
        R run = factory.createR();
        Text text = factory.createText();
        text.setValue(watermarkText);
        run.getContent().add(text);
        watermark.getContent().add(run);

        // 设置水印样式
        watermark.getPPr().setPStyle(factory.createPPrBasePStyle());
        watermark.getPPr().getPStyle().setVal("watermark");

        // 旋转和透明度设置
//        CTTextbox textBox = factory.createCTTextbox();
//        CTShape shape = factory.createCTShape();
//        shape.setStyle("position:absolute; width:500pt; height:100pt; rotation:315; opacity:0.5;");
//        shape.getEGContent().add(watermark);
//        textBox.getEGContent().add(shape);

        CTSimpleField field = factory.createCTSimpleField();
//        field.getContent().add(textBox);

        // 添加到页眉
        SdtBlock sdtBlock = factory.createSdtBlock();
        sdtBlock.getSdtContent().getContent().add(field);
//        sectPr.getEGHdrFtrReferences().add(sdtBlock);
    }

    public static void test1(){
        try {
            WordprocessingMLPackage wordPackage = WordprocessingMLPackage.createPackage();
            MainDocumentPart mainDocumentPart = wordPackage.getMainDocumentPart();
            mainDocumentPart.addStyledParagraphOfText("Title", "Hello World!");
            mainDocumentPart.addParagraphOfText("Welcome To Baeldung");
            File exportFile = new File("welcome.docx");
            wordPackage.save(exportFile);
        } catch (Docx4JException e) {
            throw new RuntimeException(e);
        }
    }

    public static void test2(){
        try {
            // <w:p>
            //        <w:pPr>
            //            <w:pStyle w:val="Title"/>
            //        </w:pPr>
            //        <w:r>
            //            <w:t>Hello World!</w:t>
            //        </w:r>
            //    </w:p>
            //每个句子都由段落 (p) 内的运行块 (run/r) 文本 (t) 表示，
            WordprocessingMLPackage wordPackage = WordprocessingMLPackage.createPackage();
            MainDocumentPart mainDocumentPart = wordPackage.getMainDocumentPart();
            ObjectFactory factory = Context.getWmlObjectFactory();
            P p = factory.createP(); //段落
            R r = factory.createR(); //运行块


            Text text = factory.createText(); //文本
            text.setValue("Welcome To Baeldung");
            r.getContent().add(text);
            p.getContent().add(r);

            RPr rPr = factory.createRPr(); //样式
            BooleanDefaultTrue b = new BooleanDefaultTrue();
            rPr.setB(b);
            rPr.setI(b);
            rPr.setCaps(b);

            Color color = factory.createColor();
            color.setVal("green");

            rPr.setColor(color);

            r.setRPr(rPr);
            mainDocumentPart.getContent().add(p);
            File exportFile = new File("welcome2.docx");
            wordPackage.save(exportFile);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void test3(){
        try {
            WordprocessingMLPackage wordPackage = WordprocessingMLPackage.createPackage();
            MainDocumentPart mainDocumentPart = wordPackage.getMainDocumentPart();
            File image = new File("img.jpg");
            byte[] bytes = Files.readAllBytes(image.toPath());
            BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.createImagePart(wordPackage, bytes);
            Inline inline = imagePart.createImageInline(
                    "Baeldung Image (filename hint)", "Alt Text", 1, 2, false);
            P Imageparagraph = addImageToParagraph(inline);
            mainDocumentPart.getContent().add(Imageparagraph);
            File exportFile = new File("welcome3.docx");
            wordPackage.save(exportFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void test4(){
        try {
            WordprocessingMLPackage wordPackage = WordprocessingMLPackage.createPackage();
            MainDocumentPart mainDocumentPart = wordPackage.getMainDocumentPart();
            ObjectFactory factory = Context.getWmlObjectFactory();
            P p = factory.createP(); //段落
            R r = factory.createR(); //运行块


            Text text = factory.createText(); //文本
            text.setValue("Welcome To Baeldung");
            r.getContent().add(text);
            p.getContent().add(r);

            int writableWidthTwips = wordPackage.getDocumentModel().getSections().get(0).getPageDimensions().getWritableWidthTwips();
            int columNum = 3;
            Tbl tbl = TblFactory.createTable(3, 3, writableWidthTwips / columNum);
            List<Object> rows = tbl.getContent();
            for (Object row : rows) {
                Tr tr = (Tr) row;
                List<Object> cells = tr.getContent();
                for (Object cell : cells) {
                    Tc td = (Tc) cell;
                    td.getContent().add(p);
                }
            }
            File exportFile = new File("welcome4.docx");
            wordPackage.save(exportFile);

            mainDocumentPart.getContent().add(tbl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static P addImageToParagraph(Inline inline) {
        ObjectFactory factory = new ObjectFactory();
        P p = factory.createP();
        R r = factory.createR();

        Drawing drawing = factory.createDrawing();
        drawing.getAnchorOrInline().add(inline);

        r.getContent().add(drawing);
        p.getContent().add(r);
        return p;
    }
}
