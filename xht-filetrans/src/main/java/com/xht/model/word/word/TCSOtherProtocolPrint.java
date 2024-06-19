package com.xht.model.word.word;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * TCS补充协议打印
 */
@Data
public class TCSOtherProtocolPrint {
    private List<TCSOtherProtocolPrintData> printDataList = new ArrayList<TCSOtherProtocolPrintData>();
    private String tcsName;
    private String homeContractDate;
    private String tcsContractDate;
    private String fujianNo;
}

