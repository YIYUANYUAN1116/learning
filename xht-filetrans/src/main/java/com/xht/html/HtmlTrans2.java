package com.xht.html;


import com.xht.model.word.TCSProtocolPrint;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;

public class HtmlTrans2 {
    public static void main(String[] args) {
        String htmlContent="<p>鉴于，甲方系东芝空调在国内唯一销售甲方，全权负责东芝空调在国内的销售及售后服务业务；乙方是一家依照中国相关法律规定设立的公司（或具有独立法人资格的其他组织），其依法设立的店面为其完全拥有的营业店面，且乙方具有空调行业家装零售业务丰富经验及提供所授权区域内提供空调安装及售后服务的能力及符合甲方关于设立专业店的相关要求。</p><p>甲乙双方本着真诚合作、平等互利、共同发展的原则，就利用乙方该店面推广、销售甲方产品事宜，经友好协商，一致达成如下协议，以期共同遵守：</p><p><strong>第一条&nbsp;区域&nbsp;</strong></p><p>乙方在合约期内的销售区域详见附件。</p><p><strong>第二条&nbsp;期限</strong></p><p>乙方在合约期内的协议期限详见附件。</p><p><strong>第三条&nbsp;销售指标</strong></p><p>乙方在合约期内的销售指标详见附件。</p><p><strong>第四条&nbsp;库存计划&nbsp;</strong></p><p>乙方在合约期内的库存计划详见附件。</p><p><strong>第五条&nbsp;订单</strong></p><p>1、乙方应以《TCS经销商订单》的格式向甲方发送订单原件，经甲方同意并将该订单上传EIP系统后，该订单生效，对双方具有约束力。</p><p>2、订单中价格均为含税价格。</p><p>3、甲方不接受任何口头或其他形式的订单。</p><p>4、若乙方于订单约定的提货日期当月最后一个工作日或本协议期满前未按照约定提取全部货物的，甲方有权在协议期满后在不通知乙方的情况下单方面取消乙方尚未提取货物的订单，若双方续约，则订单按续约后新审定价格执行。</p><p><strong>第六条&nbsp;付款方式及供货、运输</strong></p><p>1、乙方应于预计发货日前&nbsp;30&nbsp;天以书面的形式向甲方申请供货。</p><p>2、甲方在收到货款后&nbsp;15&nbsp;日，乙方可根据双方另行约定的时间至甲方指定仓库提货，乙方自行支付运费，或依据甲方不时书面通知的发货政策执行。</p><p>3、乙方收到货物时应当在货物交接文件上签字确认，此时视为甲方交货完成。</p><p><strong>第七条&nbsp;质量保证&nbsp;</strong></p><p>乙方在合约期内的质量保证详见附件。</p><p><strong>第八条&nbsp;销售奖励政策</strong>&nbsp;</p><p>乙方在合约期内的销售奖励政策详见附件。</p><p><strong>第九条&nbsp;退（换）货政策&nbsp;</strong></p><p>乙方如发现产品存在质量问题，应自收到货物之日起五日内向甲方以书面方式提出（人为损坏除外），经甲方确认后确属产品质量问题的，则由甲方负责更换并承担运费。</p><p><strong>第十条&nbsp;协议的终止与续订</strong></p><p>乙方在合约期内的协议的终止与续订详见附件。</p><p><strong>第十一条&nbsp;甲、乙双方的权利及义务</strong></p><p>（一）、甲方的权利和责任</p><p>1、甲方提供专业店授权牌，不定期推出新款产品。</p><p>2、向乙方提供有关的产品文件、宣传资料。</p><p>3、协助乙方培训具有专业技术知识的经营销售人员。</p><p>4、按照协议的规定向乙方提供合格产品，产品如发现质量问题依照本协议规定处理。</p><p>5、乙方如在进行市场推广活动中需要使用甲方的商标，需经甲方审核同意后，方可在规定的范围内使用。</p><p>6、甲方有权在协议期限内，根据市场及生产成本的变更对供货价格进行调整，并在新价格变更生效前一个月以书面形式通知乙方。</p><p>7、甲方有权对乙方经销行为进行考察、评判，对违反本协议的行为有权按照本协议规定进行处理，如乙方行为导致甲方有损失的，甲方有权从乙方在甲方账面上及附件约定的返利中扣除相应损失，仍不够赔偿的，甲方还有权向乙方追偿。</p><p>（二）、乙方的权利和责任</p><p>1、乙方应按照甲方与乙方签订的付款条件及时支付货款。</p><p>2、乙方按期提出需货计划，市场预测。</p><p>3、记录：乙方应始终对以下事项进行完整正确的记录：</p><p>(1) 产品每周、每月的进销存数据；</p><p>(2) 预计4个月的产品进货数据；</p><p>(3) 乙方提供的售后服务和其它重要细节。</p><p>4、乙方及时做好市场调查、广告宣传等工作并做好客人对产品建议、产品质量信息的反馈。</p><p>5、乙方的经营面积不得小于&nbsp;50&nbsp;平方米，按照甲方《东芝中央空调专业店装修手册》设计店面。</p><p>6、乙方应通过在其所拥有的陈列室、维修场所和营业车辆上及在广告中显著地展示甲方的主要标识和商标的方式来鉴别其营业地点。除非为推广、销售产品和提供售后服务所必需，乙方不得使用甲方的商标或商号。乙方应立即停止任何甲方以书面形式表示反对的,对甲方商标的宣传、展示或其它使用。本协议书期满或终止后，乙方不得擅自注册或使用与甲方的主要标示和商标全部或部分相同或容易导致公众混淆或与中外文的原文相类似或与其汉语拼音、中外文翻译相类似的商标、标识、商号、域名，也不得享有上述知识产权的注册权，或试图将其注册在自己名下。上述知识产权的申请权和注册权均属于甲方。如果本协议书签署之前，乙方已经注册了任何与甲方的主要标示和商标相同或近似的商标、商号、域名等，应在签署本协议书之前无偿转让给甲方。本协议书期满或终止后，乙方应：停止以任何形式使用甲方商标；并立即除去乙方持有的任何产品及所有招牌、发票、广告及其它材料上标明的甲方商标。乙方违反上述规定，则甲方为执行乙方停止使用上述商标和商品名称的合理费用（包括但不限于律师费、诉讼费、财产保全担保费、公证费等）由乙方支付。</p><p>7、未经甲方允许，不得将产品销售给经营与甲方产品具有直接竞争关系的厂家或经销商。</p><p>8、甲方所供应产品的商品品牌、形象及标识之版权为甲方所拥有，乙方在行使专业店职责期间应对甲方品牌、形象及标识进行义务保护。乙方在合约期内应对专业店东芝LOGO门头、内部装饰（含所有东芝LOGO元素的背景、灯箱等等）进行保护，如甲方发现乙方擅自更改或撕毁等不当行为，在甲方书面或口头通知改正之日起30日内仍未改正的，甲方有权取消其协议期限内相关销售返利。</p><p>9、乙方如发现假冒、仿冒商品或不正当竞争行为，应及时通知甲方并协助甲方收集相关证据。未经允许，乙方不得采取撕毁、涂抹等不正当方式损坏甲方产品整机、产品零部件及其包装上任何商标、标识、铭牌，亦不得协助或参与其他第三方采取前述或其他任何方式进行不正当竞争行为损害甲方利益或企业形象。如甲方发现乙方从事前述不当行为，甲方有权取消其协议期限内相关销售返利。</p><p>10、除本协议另有约定，乙方应在本协议规定的授权区域市场内进行经销。</p><p>11、乙方需在其授权区域内的城市支持并维护甲方所提供的该城市所属“设计师网”，并承担该网站的日常维护费用，做好与该网站合作的装潢设计师及客户的沟通交流。</p><p>12、乙方需要对其所属的零售店进行管理，并按甲方要求在取得用户同意的前提下对家庭用户项目信息进行网上备案，并监督零售店执行同样的家庭用户项目信息备案。</p><p>13、乙方正常完成本协议规定的年度销售指标及其它各项营销指标后，并满足甲方约定的条件后，可享受甲方承诺的奖励。</p><p>14、除了乙方与甲方之间的销售合同中所约定的产品保证外，乙方不得扩展任何额外的产品保证。</p><p>15、若由下列原因而引起的产品质量问题, 乙方承诺损失由其自行承担：</p><p>&nbsp;&nbsp;(1) 乙方或其指定的第三方运输、存储不当而造成产品腐蚀及损耗；</p><p>&nbsp;&nbsp;(2) 乙方不按规定指示或在不被认可的工程标准下进行工程安装；</p><p>&nbsp;&nbsp;(3) 乙方在未经甲方书面认可的情况下擅自维修或使用不当；</p><p>&nbsp;&nbsp;(4) 乙方在维修时使用不适合或不合格的替代材料。</p><p>16、乙方应按照甲方提供的指南和标准安装已售产品并提供售后服务。如乙方提供的安装或售后服务不符甲方标准经甲方指出后须立即改正。</p><p>17、乙方不得销售仿冒、假冒甲方商品。</p><p>18、本协议因期满自动终止或任何一方行使解除权或因其他任何原因双方终止合作的，乙方尚未销售完毕的剩余产品由乙方自行处理。乙方与客户之间尚未履行的合同、订单或保修义务应移交给甲方或甲方指定的第三方，并配合完成相关的费用结算及交接手续，否则因此产生的一切责任由乙方自行承担。</p><p>19、当政府部门或者其他第三方对甲方产品进行市场质量监督抽查活动时，乙方应第一时间书面通知甲方抽查相关信息，包括但不限于产品型号以及抽检测试项目等，以便甲方及时跟进支持。因延误通知或者不告知而引起的后果，乙方应自行承担相应的法律责任，并承担因此给甲方造成的所有损失。</p><p><strong>第十二条&nbsp;违约责任</strong></p><p>1、如乙方发生下列违约情形，经甲方书面或口头提醒更正后在10个工作日内仍未改正的，甲方即有权书面通知乙方而终止本协议，取消乙方的经销商资格，并要求乙方赔偿因此给甲方造成的全部损失：</p><p>(1) 乙方违反本协议第十一条约定的；</p><p>(2) 乙方有其他严重违反《经销协议书》及《东芝空调TCS专业店经销商手册》及本协议约定情形的；</p><p>2、乙方的违约行为给甲方造成损失的，甲方有权追究其违约责任并要求乙方赔偿。</p><p>3、因乙方违反本协议项下任何一项约定所应承担的违约责任和赔偿责任，甲方均有权就所受到的损失包括但不限于诉讼费、赔偿金、相关花销、律师费、财产保全担保费等向乙方追偿。甲方有权直接从应付乙方的任何款项（包括但不限于预付款、返利等）中扣除上述赔偿额，不足部分乙方仍应支付。</p><p><strong>第十三条&nbsp;责任限制</strong></p><p>1、 无论本协议及本协议提及的相关文件中是否有任何相反规定，除产品本身质量缺陷造成的损失外，甲方或产品的制造商均不应对在本协议或其他项下供应的产品或其部件的安装或使用产生的任何间接、附随或其他的损坏赔偿金承担责任，对因此产生的乙方或其代理人、分包商或使用人的人身或财产损失亦不承担责任。</p><p>2、对乙方所进行的不当保养或维修，甲方不负任何责任。就该等索赔或公诉，乙方应主动通过自己的律师为该等索赔或公诉进行答辩，并自行承担因此产生的一切责任，甲方对此不承担任何直接或连带责任。</p><p>3、甲方基于本协议需要承担损害赔偿责任的，仅对实际直接损失承担赔偿责任。甲方应当承担的全部赔偿责任（包括但不限于违约金）不超过本协议总金额的100%。</p><p><strong>第十四条&nbsp;适用法律及争议的解决</strong></p><p>1、本协议签署、效力、解释、执行和争议的解决均适用中华人民共和国法律法规。&nbsp;</p><p>2、甲、乙双方如发生之争议无法协商解决，将提交甲方所在地法院解决。</p><p>3、若由于不可抗力致使一方未能全部或部分履行本协议，经书面通知另一方，本合同内受到影响之条款可在不能履行之期间及受影响之范围内终止履行。</p><p><strong>第十五条&nbsp;协议的变更和补充</strong></p><p>1、签订本协议同时乙方应当与甲方签订《经销协议书》及《东芝空调TCS专业店经销商手册》，遵守该协议书及手册中的各项条款。</p><p>2、本协议的变更需经双方同意，并以书面形式体现。协议未尽事宜，经双方友好协商后，以补充协议的方式加以完善，补充协议对本合同条款作出变更的，以补充协议约定为准，有多份补充协议的，以最后签署的补充协议为准。&nbsp;</p><p>3、本协议一式两份，双方各持一份，自双方盖章日起生效。</p>";
        String areaText ="<p>甲方指定乙方<span class=\"mention\" data-index=\"0\" data-denotation-char=\"@\" data-id=\"1\" data-value=\"{shop}\">\uFEFF<span contenteditable=\"false\">@{shop}</span>\uFEFF</span> 店面为其&nbsp;<span class=\"mention\" data-index=\"1\" data-denotation-char=\"@\" data-id=\"2\" data-value=\"{product}\">\uFEFF<span contenteditable=\"false\">@{product}</span>\uFEFF</span> 产品在<span class=\"mention\" data-index=\"2\" data-denotation-char=\"@\" data-id=\"3\" data-value=\"{area}\">\uFEFF<span contenteditable=\"false\">@{area}</span>\uFEFF</span> 区域市场专业店。乙方不得向授权区域以外进行主动报价、销售，但是，甲方同意乙方应非其销售区域的个别客户的主动要求而超出“区域”向该客户销售商品或服务。因从事项目销售对资金能力、人员、销售策略等方面与从事家装零售销售完全不同，乙方仅允许从事以家庭用户为直接销售对象的家装零售业务，如乙方有意愿从事项目批发销售业务，须经甲方考核认定并取得甲方授权方可从事项目批发销售。</p>";
        String periodText = "<p>本协议期限为<span class=\"mention\" data-index=\"0\" data-denotation-char=\"@\" data-id=\"1\" data-value=\"{dateRange}\">\uFEFF<span contenteditable=\"false\">@{dateRange}</span>\uFEFF</span> ，为期<span class=\"mention\" data-index=\"1\" data-denotation-char=\"@\" data-id=\"2\" data-value=\"{month}\">\uFEFF<span contenteditable=\"false\">@{month}</span>\uFEFF</span> 。期满时除经双方协商一致延长外，本协议自动终止，但双方应按照第十二条第4款约定处理后续事宜。</p>";
        String areaTextReplace = "乙方在合约期内的销售区域详见附件。";
        String periodTextReplace = "乙方在合约期内的协议期限详见附件。";
        try {
            htmlContent = htmlContent.replaceAll(areaTextReplace,areaText);
            htmlContent = htmlContent.replaceAll(periodTextReplace,periodText);
            convertHtmlToDocx(htmlContent, "output.docx");
            TCSProtocolPrint tcsProtocolPrint = new TCSProtocolPrint();
            tcsProtocolPrint.setProduct("西瓜");
            tcsProtocolPrint.setArea("上海青浦");
            tcsProtocolPrint.setDateRange("2023-1 至 2024-1");
            tcsProtocolPrint.setMonth("12个月");
            System.out.println("Word document created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void convertHtmlToDocx(String html, String outputFile) throws Exception {
        html = html.replaceAll("&nbsp;"," ");
        // 使用Jsoup解析HTML
        Document doc = Jsoup.parse(html);

        // 创建一个新的Word文档
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();

        // 导入HTML内容
        XHTMLImporterImpl xhtmlImporter = new XHTMLImporterImpl(wordMLPackage);
        wordMLPackage.getMainDocumentPart().getContent().addAll(xhtmlImporter.convert(doc.html(), null));

        // 保存Word文档
        wordMLPackage.save(new File(outputFile));
    }


}
