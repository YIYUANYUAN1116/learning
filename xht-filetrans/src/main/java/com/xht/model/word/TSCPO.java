package com.xht.model.word;

import lombok.Data;

import java.util.List;

@Data
public class TSCPO {
    private String printTitle;
    private String entityName;
    private String regionalCode;
    private String registAddress;
    private String connectNm;
    private String fax;
    private String tel;
    private String signingDate;
    private String deliveryDate;
    private List<TSCPODetail> detailList;
    private String totalPrice;

}
