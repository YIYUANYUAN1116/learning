package com.xht.utils;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.ognl.Ognl;
import org.docx4j.XmlUtils;
import org.docx4j.model.structure.HeaderFooterPolicy;
import org.docx4j.model.structure.SectionWrapper;
import org.docx4j.openpackaging.io.SaveToZipFile;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.FooterPart;
import org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Document;
import org.docx4j.wml.Ftr;
import org.docx4j.wml.Hdr;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class XhtFileOperationUtil {

    public static void createDocxFile(File templateFile,
                                      File outputFile,
                                      Object data, Class<?> dataType) throws Exception{
        WordprocessingMLPackage wmp = WordprocessingMLPackage.load(templateFile);
        MainDocumentPart mainPart = wmp.getMainDocumentPart();
        Document doc = mainPart.getJaxbElement();
        String xml = XmlUtils.marshaltoString(doc, true, true);

        String resultXml = injectDataToXmlTemplate(xml, data, dataType);

        Object newDoc = XmlUtils.unmarshallFromTemplate(resultXml, new HashMap<String, String>());
        mainPart.setJaxbElement((Document)newDoc);

        List<SectionWrapper> sectionWrappers = wmp.getDocumentModel().getSections();
        for (SectionWrapper sw : sectionWrappers) {
            HeaderFooterPolicy hfp = sw.getHeaderFooterPolicy();
            if(hfp.getDefaultHeader() != null){
                HeaderPart hdrPart = hfp.getDefaultHeader();
                Hdr hdr = hdrPart.getJaxbElement();

                xml = XmlUtils.marshaltoString(hdr, true, true);
                resultXml = injectDataToXmlTemplate(xml, data, dataType);

                newDoc = XmlUtils.unmarshallFromTemplate(resultXml, new HashMap<String, String>());
                hdrPart.setJaxbElement((Hdr)newDoc);

                HeaderPart hdrPart1 = hfp.getHeader(1);
                Hdr hdr1 = hdrPart1.getJaxbElement();

                xml = XmlUtils.marshaltoString(hdr1, true, true);
                resultXml = injectDataToXmlTemplate(xml, data, dataType);

                newDoc = XmlUtils.unmarshallFromTemplate(resultXml, new HashMap<String, String>());
                hdrPart1.setJaxbElement((Hdr)newDoc);
            }

            if(hfp.getDefaultFooter() != null){
                FooterPart ftrPart = hfp.getDefaultFooter();

                Ftr hdr = ftrPart.getJaxbElement();

                xml = XmlUtils.marshaltoString(hdr, true, true);

                resultXml = injectDataToXmlTemplate(xml, data, dataType);

                newDoc = XmlUtils.unmarshallFromTemplate(resultXml, new HashMap<String, String>());
                ftrPart.setJaxbElement((Ftr)newDoc);


                FooterPart ftrPart1 = hfp.getFooter(1);

                Ftr ftr = ftrPart1.getJaxbElement();

                xml = XmlUtils.marshaltoString(ftr, true, true);

                resultXml = injectDataToXmlTemplate(xml, data, dataType);

                newDoc = XmlUtils.unmarshallFromTemplate(resultXml, new HashMap<String, String>());
                ftrPart1.setJaxbElement((Ftr)newDoc);
            }

        }
        //output
        SaveToZipFile saver = new SaveToZipFile(wmp);
        saver.save(outputFile);
    }


    private static String injectDataToXmlTemplate(String xml, Object data, Class<?> dataType) throws Exception{
        StringBuilder resultXml = new StringBuilder(xml);

        PropertyDescriptor[] properties = Introspector.getBeanInfo(dataType).getPropertyDescriptors();
        List<TemplateNameProperty> listFieldList = new ArrayList<TemplateNameProperty>();
        List<TemplateNameProperty> simpleFieldList = new ArrayList<TemplateNameProperty>();

        PropertyDescriptor property = null;
        for(int i = 0; i < properties.length; i++) {
            property = properties[i];
            List.class.isAssignableFrom(property.getPropertyType());
            if(isList(property.getPropertyType())) {
                listFieldList.add(new TemplateNameProperty(getListTemplateNamePrefix(property.getName()), property));
            } else if (isArray(property.getPropertyType())) {
                listFieldList.add(new TemplateNameProperty(getListTemplateNamePrefix(property.getName()), property));
            } else {
                //non list, array
                simpleFieldList.add(new TemplateNameProperty(getSimpleTemplateName(property.getName()), property));
            }
        }

        //scan the xml
        int pos = 0;
        int replaceEnd = 0;
        int k = 0;
        TemplateNameProperty namePropTmp = null;
        int trBegin = 0;
        int trEnd = 0;
        boolean isJumpToNextLoop = false;
        String val;

        while(true) {
            pos = resultXml.indexOf("${", pos);

            if(pos < 0) {
                break;
            }

            isJumpToNextLoop = false;
            for(k = 0; k < simpleFieldList.size(); k++) {
                namePropTmp = simpleFieldList.get(k);

                if(isStringBuilderStartsWith(resultXml, pos, namePropTmp.getTemplateName())) {
                    //matched
                    replaceEnd = pos + namePropTmp.getTemplateName().length();

                    //replace
                    val = String.valueOf(namePropTmp.getProperty().getReadMethod().invoke(data, (Object[])null)).replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&apos;");
                    resultXml.replace(pos, replaceEnd, val);

                    //move to next
                    pos += val.length();
                    isJumpToNextLoop = true;
                    break;
                }
            }

            if(isJumpToNextLoop) {
                continue;
            }

            isJumpToNextLoop = false;
            for(k = 0; k < listFieldList.size(); k++) {
                namePropTmp = listFieldList.get(k);

                if(isStringBuilderStartsWith(resultXml, pos, namePropTmp.getTemplateName())) {
                    //matched
                    //find the tr xml. ("<w:tr ",  "</w:tr>" )
                    trBegin = backwardSearch(resultXml, pos, "<w:tr ", "<w:tr>");
                    trEnd = resultXml.indexOf("</w:tr>", pos);

                    if(trBegin < 0 || trEnd < 0) {
                        pos += 2;

                        isJumpToNextLoop = true;
                        break;
                    } else {
                        //find tr
                        trEnd += 7;

                        pos = injectAllTr(resultXml, trBegin, trEnd, namePropTmp, data);

                        isJumpToNextLoop = true;
                        break;
                    }
                }
            }
            if(isJumpToNextLoop) {
                continue;
            }

            pos += 2;
        }

        return resultXml.toString();
    }

    protected static boolean isList(Class<?> cls) {
        //return IsInterfaceType(cls, List.class);
        return List.class.isAssignableFrom(cls);
    }

    protected static boolean isArray(Class<?> cls) {
        return cls.isArray();
    }

    protected static String getListTemplateNamePrefix(String fieldName) {
        return "${" + fieldName + "[#i].";
    }

    protected static String getSimpleTemplateName(String fieldName) {
        return "${" + fieldName + "}";
    }

    protected static boolean isStringBuilderStartsWith(StringBuilder sb, int pos, String val) {
        if(sb.length() < (pos + val.length())) {
            return false;
        }

        for(int i = 0; i < val.length(); i++) {
            if(sb.charAt(pos + i) != val.charAt(i)) {
                return false;
            }
        }

        return true;
    }

    protected static int injectAllTr(StringBuilder xml, int trBegin, int trEnd, TemplateNameProperty tempNameProp, Object data) throws Exception {
        Object list = tempNameProp.getProperty().getReadMethod().invoke(data, (Object[])null);
        int rowCount = 0;

        if(list != null) {
            if(isArray(tempNameProp.getProperty().getPropertyType())) {
                rowCount = Array.getLength(list);
            } else {
                rowCount = ((List)list).size();
            }
        }

        if(rowCount == 0) {
            //clear tr value
            StringBuilder trXml = new StringBuilder(xml.substring(trBegin, trEnd));
            injectEmptyTr(trXml, tempNameProp);

            xml.replace(trBegin, trEnd, trXml.toString());

            return trBegin + trXml.length();
        } else {
            String trXml = xml.substring(trBegin, trEnd);
//			logger.debug("trXml-------\r\n" + trXml);
//
            xml.delete(trBegin, trEnd);
//			logger.debug("xml-------\r\n" + xml);

            int insertPos = trBegin;
            StringBuilder trXmlTmp = null;
            for(int i = 0; i < rowCount; i++) {
                trXmlTmp = new StringBuilder(trXml);

                //inject
                injectTr(trXmlTmp, tempNameProp, i, data);

                xml.insert(insertPos, trXmlTmp.toString());

                insertPos += trXmlTmp.length();
            }

            return insertPos;
        }

    }

    protected static void injectTr(StringBuilder trXml, TemplateNameProperty tempNameProp, int rowIndex, Object data) throws Exception {
        int pos = 0;
        int pos2 = 0;

        String expression = null;
        String value = null;
        String br = "</w:t></w:r></w:p><w:p w:rsidR=\"006F433C\" w:rsidRDefault=\"006F433C\" w:rsidP=\"009762CE\"><w:pPr><w:pStyle w:val=\"ac\"/><w:spacing w:line=\"360\" w:lineRule=\"auto\"/><w:ind w:firstLineChars=\"0\" w:firstLine=\"0\"/><w:rPr><w:rFonts w:ascii=\"华文中宋\" w:eastAsia=\"华文中宋\" w:hAnsi=\"华文中宋\"/><w:szCs w:val=\"21\"/></w:rPr></w:pPr><w:r><w:rPr><w:rFonts w:ascii=\"华文中宋\" w:eastAsia=\"华文中宋\" w:hAnsi=\"华文中宋\" w:hint=\"eastAsia\"/><w:szCs w:val=\"21\"/></w:rPr><w:t xml:space=\"preserve\">";
        while(true) {
            pos = trXml.indexOf(tempNameProp.getTemplateName(), pos);

            if(pos < 0) {
                break;
            }

            pos2 = trXml.indexOf("}", pos + 2);
            if(pos2 < 0) {
                break;
            }

            //replace the rowIndex
            expression = trXml.substring(pos+2, pos2).replaceAll("#i", String.valueOf(rowIndex));
            value = String.valueOf(Ognl.getValue(expression, data)).replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&apos;").replace("quarterPercentBr", br);

            pos2 ++;
            trXml.replace(pos, pos2, value);

            pos = pos2;
        }
    }


    protected static void injectEmptyTr(StringBuilder trXml, TemplateNameProperty tempNameProp) {
        int pos = 0;
        int pos2 = 0;

        while(true) {
            pos = trXml.indexOf(tempNameProp.getTemplateName(), pos);

            if(pos < 0) {
                break;
            }

            pos2 = trXml.indexOf("}", pos + 2);
            if(pos2 < 0) {
                break;
            }

            trXml.delete(pos, pos2 + 1);

            pos = pos2;
        }
    }

    protected static int backwardSearch(StringBuilder sb, int pos, String val1, String val2) {
        int curPos = pos - val1.length();

        while(curPos >= 0) {
            if(isStringBuilderStartsWith(sb, curPos, val1)
                    || isStringBuilderStartsWith(sb, curPos, val2)
            ) {
                return curPos;
            }

            curPos --;
        }

        return -1;
    }

    protected static int backwardSearch(StringBuilder sb, int pos, String val) {
        int curPos = pos - val.length();

        while(curPos >= 0) {
            if(isStringBuilderStartsWith(sb, curPos, val)) {
                return curPos;
            }

            curPos --;
        }

        return -1;
    }


    private static void wordToPDF(ActiveXComponent app, Dispatch docs, String sFilePath, String toFileName) throws Exception{
        log.info("启动Word...");
        long start = System.currentTimeMillis();
        Dispatch doc = null;
        int wdFormatPDF = 17;
        try {
            doc = Dispatch.invoke(docs, "Open", Dispatch.Method, new Object[] {
                    sFilePath, false, false }, new int[1]).toDispatch();
            log.info("打开文档...");
            File tofile = new File(toFileName);
            if (tofile.exists()) {
                tofile.delete();
            }
            Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[] {
                    toFileName, wdFormatPDF }, new int[1]);
            long end = System.currentTimeMillis();
            log.info("转换完成..用时：" + (end - start) + "ms.");
        } catch (Exception e) {
            log.error("========Error:文档转换失败：" + e.getMessage());
            throw e;
        } finally {
            if (doc != null){
                Dispatch.invoke(doc,"Close",Dispatch.Method,new Object[] { new Variant(false)}, new int[1]);
            }
        }
    }

    public static void wordToPDF( String sFilePath, String tFilePath) throws Exception{
        //生成pdf文件
        ActiveXComponent app = null;
        long start = System.currentTimeMillis();
        Dispatch doc = null;
        int wdFormatPDF = 17;
        try {
            log.info("启动Word...");
            
            ComThread.InitSTA();
            app = new ActiveXComponent("Word.Application");
            app.setProperty("Visible", new Variant(false));
            doc = Dispatch.get(app, "Documents").toDispatch();
            doc = Dispatch.invoke(doc, "Open", Dispatch.Method, new Object[] {
                    sFilePath, false, false }, new int[1]).toDispatch();
            log.info("打开文档...");
            File tofile = new File(tFilePath);
            if (tofile.exists()) {
                tofile.delete();
            }
            Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[] {
                    tFilePath, wdFormatPDF }, new int[1]);
            
            long end = System.currentTimeMillis();
            log.info("转换完成..用时：" + (end - start) + "ms.");
        } catch (Exception e) {
            log.error("========Error:文档转换失败：" + e.getMessage());
            throw e;
        } finally {
            if (doc != null){
                Dispatch.invoke(doc,"Close",Dispatch.Method,new Object[] { new Variant(false)}, new int[1]);
            }
            if (app != null){
                app.invoke("Quit", new Variant[] {});
                app.safeRelease();
            }
            ComThread.Release();
            log.info("关闭文档");
        }
    }


    public static byte[] convertFileToByteArray(File file) throws IOException {
        byte[] bytesArray = new byte[(int) file.length()];

        // 创建一个文件输入流
        FileInputStream fis = new FileInputStream(file);

        // 读取文件内容到字节数组
        fis.read(bytesArray);

        // 关闭文件输入流
        fis.close();

        return bytesArray;
    }
}
