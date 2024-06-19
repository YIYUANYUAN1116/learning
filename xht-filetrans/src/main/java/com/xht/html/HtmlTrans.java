package com.xht.html;

import org.apache.commons.text.StringEscapeUtils;

import java.util.HashMap;
import java.util.Map;

public class HtmlTrans {
    public static void main(String[] args) {
        String str = "<p>甲方指定乙方<span class=\"mention\" data-index=\"0\" data-denotation-char=\"@\" data-id=\"1\" data-value=\"{shop}\">\uFEFF<span contenteditable=\"false\">@{shop}</span>\uFEFF</span> 店面为其&nbsp;<span class=\"mention\" data-index=\"1\" data-denotation-char=\"@\" data-id=\"2\" data-value=\"{product}\">\uFEFF<span contenteditable=\"false\">@{product}</span>\uFEFF</span> 产品在<span class=\"mention\" data-index=\"2\" data-denotation-char=\"@\" data-id=\"3\" data-value=\"{area}\">\uFEFF<span contenteditable=\"false\">@{area}</span>\uFEFF</span> 区域市场专业店。" +
                "乙方不得向授权区域以外进行主动报价、销售，但是，甲方同意乙方应非其销售区域的个别客户的主动要求而超出“区域”向该客户销售商品或服务。因从事项目销售对资金能力、人员、销售策略等方面与从事家装零售销售完全不同，乙方仅允许从事以家庭用户为直接销售对象的家装零售业务，" +
                "如乙方有意愿从事项目批发销售业务，须经甲方考核认定并取得甲方授权方可从事项目批发销售。</p>" +
                "<p>如甲方家庭用户项目信息备案系统（以下称为“系统”）中显示乙方针对某家庭用户点对点推广甲方空调产品， 且乙方按照甲方要求在系统中提交相应的推广产品样本、推广方案图纸、现场照片等证明材料并经甲方确认有效的：</p><p>如系统中显示乙方为首个向该家庭用户进行点对点推广的ＴＣＳ经销商，且乙方最终向该家庭用户成功销售甲方空调产品的，甲方将给予乙方就该笔交易实际从甲方提货额的&nbsp;<span class=\"mention\" data-index=\"0\" data-denotation-char=\"@\" data-id=\"1\" data-value=\"{firstSucceedSales}\">\uFEFF<span contenteditable=\"false\">@{firstSucceedSales}</span>\uFEFF</span> 作为销售返利；</p><p>如系统中显示乙方为首个向该家庭用户进行点对点推广的ＴＣＳ经销商，但由甲方其他经销商最终向该家庭用户成功销售甲方空调产品的，甲方仍将给予乙方就其他经销商在该笔交易中实际从甲方提货额的&nbsp;<span class=\"mention\" data-index=\"1\" data-denotation-char=\"@\" data-id=\"2\" data-value=\"{firstSucceed}\">\uFEFF<span contenteditable=\"false\">@{firstSucceed}</span>\uFEFF</span> 作为销售返利；" +
                "</p><p>如乙方向某家庭用户最终成功销售甲方空调产品，但系统中显示乙方非首个向该家庭用户进行点对点推广的ＴＣＳ经销商的，甲方将给予乙方就该笔交易实际从甲方提货额的&nbsp;<span class=\"mention\" data-index=\"2\" data-denotation-char=\"@\" data-id=\"3\" data-value=\"{afterFirstSales}\">\uFEFF<span contenteditable=\"false\">@{afterFirstSales}</span>\uFEFF</span> 作为销售返利。</p><p>本项销售返利的总额不应超过乙方提货额的<span class=\"mention\" data-index=\"3\" data-denotation-char=\"@\" data-id=\"4\" data-value=\"{maxSalesPercent}\">\uFEFF<span contenteditable=\"false\">@{maxSalesPercent}</span>\uFEFF</span> 。</p><p><br></p>";

        String removedHtmlTags = removeHtmlTags(str);
        removedHtmlTags = StringEscapeUtils.unescapeHtml4(removedHtmlTags);
        System.out.println(removedHtmlTags);

        // 替换标记
        Map<String, String> map = new HashMap<>();
        map.put("shop","上海");
        map.put("product","上海");
        map.put("area","上海");

        removedHtmlTags = replacePlaceholders(removedHtmlTags, map);

        System.out.println(removedHtmlTags);
    }

    public static String removeHtmlTags(String htmlContent) {
        // 使用正则表达式去除HTML标签
        return htmlContent.replaceAll("<[^>]*>", "");
    }

    public static String replacePlaceholders(String text, String shop, String product, String area) {
        // 替换 @{shop} 为指定文本
        text = text.replaceAll("@\\{shop}", shop);
        // 替换 @{product} 为指定文本
        text = text.replaceAll("@\\{product}", product);
        // 替换 @{area} 为指定文本
        text = text.replaceAll("@\\{area}", area);

        return text;
    }

    public static String replacePlaceholders(String text, Map<String,String> param) {
        String str = text;
        for (String key : param.keySet()) {
            String regex = "@\\{"+key+"}";
            text = text.replaceAll(regex, param.get(key));
        }
        return text;
    }



}
