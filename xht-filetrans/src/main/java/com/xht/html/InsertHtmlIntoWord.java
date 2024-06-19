package com.xht.html;

import org.docx4j.XmlUtils;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;

import java.io.File;
import java.util.List;

public class InsertHtmlIntoWord {
    public static void main(String[] args) {
        try {
            // 加载现有的 Word 文档
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new File("D:\\Development\\BuisFile\\testfile\\销售模块-TCS协议打印 - 副本.docx"));

            // 定义要插入的 HTML 内容
            String periodText = "<p>本协议期限为<span class=\"mention\" data-index=\"0\" data-denotation-char=\"@\" data-id=\"1\" data-value=\"{dateRange}\">\uFEFF<span contenteditable=\"false\">@{dateRange}</span>\uFEFF</span> ，为期<span class=\"mention\" data-index=\"1\" data-denotation-char=\"@\" data-id=\"2\" data-value=\"{month}\">\uFEFF<span contenteditable=\"false\">@{month}</span>\uFEFF</span> 。期满时除经双方协商一致延长外，本协议自动终止，但双方应按照第十二条第4款约定处理后续事宜。</p>";

            // 创建 XHTMLImporterImpl 实例
            XHTMLImporterImpl xhtmlImporter = new XHTMLImporterImpl(wordMLPackage);

            // 将 HTML 内容转换为 Word 中的对象
            List<Object> contentList = xhtmlImporter.convert(periodText, null);

            // 获取主文档部分
            MainDocumentPart mainDocumentPart = wordMLPackage.getMainDocumentPart();
            
            // 将转换后的内容添加到文档末尾
            List<Object> content = mainDocumentPart.getContent();
            mainDocumentPart.getContent().addAll(contentList);

            // 保存修改后的文档
            wordMLPackage.save(new File("D:\\Development\\BuisFile\\testfile\\销售模块-TCS协议打印 - 副本insert.docx"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
