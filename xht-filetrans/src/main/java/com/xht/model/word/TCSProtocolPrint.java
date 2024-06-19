package com.xht.model.word;

import lombok.Data;

/**
 * TCS打印
 */
@Data
public class TCSProtocolPrint {
    private String tcsName; //乙方
    private String tcsAddress; //地址
    private String tcsZip;//邮编
    private String tcsTel;//电话
    private String tcsFax;//传真
    private String tcsStoreAddress;//店面地址
    private String product;//产品
    private String regional;//区域
    private String startDate;//开始时间
    private String endDate;//结束时间
    private String startToEnd;//为期
    private String purchaseAmount;//提货额
    private String monthInventory;//库存额
    private String applicationSupplyDays;//发货日前
    private String deliveryDays;//货款后
    private String servicePersonNum;//售后人数
    private String trainingPersonNum;//培训人数
    private String protocolyear;//年度协议
    private String terminateBeforeMonths;//到期前
    private String terminatePercentCn;//百分之
    private String terminatePercentNum;

    private String area;//区域
    private String dateRange;//期限
    private String month;//为期
    private String shop;//
    private String firstMiniPickup;//提货额
    private String miniMonthlyInventory;//库存额

}
