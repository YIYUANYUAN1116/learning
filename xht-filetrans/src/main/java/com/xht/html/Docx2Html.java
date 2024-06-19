package com.xht.html;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.docx4j.Docx4J;
import org.docx4j.Docx4jProperties;
import org.docx4j.convert.out.HTMLSettings;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;


public class Docx2Html  {

    static {

        inputfilepath = "D:\\Development\\BuisFile\\testfile\\销售模块-TCS协议打印 - 副本.docx";
        save = true;
        nestLists = true;
    }

    static boolean save;
    static boolean nestLists;
    static String inputfilepath;

    public static void main(String[] args) throws Exception {
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage
                .load(new File("D:\\Development\\BuisFile\\testfile\\销售模块-TCS协议打印 - 副本.docx"));

        HTMLSettings htmlSettings = Docx4J.createHTMLSettings();

        htmlSettings.setImageDirPath(inputfilepath + "_files");
        htmlSettings.setImageTargetUri(inputfilepath.substring(inputfilepath.lastIndexOf("/") + 1) + "_files");
        htmlSettings.setWmlPackage(wordMLPackage);

        String userCSS = null;
        if (nestLists) {
            userCSS = "html, body, div, span, h1, h2, h3, h4, h5, h6, p, a, img,  table, caption, tbody, tfoot, thead, tr, th, td "
                    + "{ margin: 0; padding: 0; border: 0;}" + "body {line-height: 1;} ";
        } else {
            userCSS = "html, body, div, span, h1, h2, h3, h4, h5, h6, p, a, img,  ol, ul, li, table, caption, tbody, tfoot, thead, tr, th, td "
                    + "{ margin: 0; padding: 0; border: 0;}" + "body {line-height: 1;} ";

        }
        htmlSettings.setUserCSS(userCSS);

        OutputStream os;
        if (save) {
            os = new FileOutputStream(inputfilepath + ".html");
        } else {
            os = new ByteArrayOutputStream();
        }

        Docx4jProperties.setProperty("docx4j.Convert.Out.HTML.OutputMethodXML", true);

        Docx4J.toHTML(htmlSettings, os, Docx4J.FLAG_EXPORT_PREFER_XSL);

        if (save) {
            System.out.println("Saved: " + inputfilepath + ".html ");
        } else {
            System.out.println(((ByteArrayOutputStream) os).toString());
        }

        if (wordMLPackage.getMainDocumentPart().getFontTablePart() != null) {
            wordMLPackage.getMainDocumentPart().getFontTablePart().deleteEmbeddedFontTempFiles();
        }
        htmlSettings = null;
        wordMLPackage = null;
    }
}