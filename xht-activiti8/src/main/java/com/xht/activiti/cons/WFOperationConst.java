package com.xht.activiti.cons;

public class WFOperationConst {


    /**
     * 申请提出 {@value}
     */
    public static final String APPLY = "apply";
    /**
     * 承认 {@value}
     */
    public static final String APPROVE = "approve";
    /**
     * 拒绝: 直接停止整个流程
     */
    public static final String REJECT = "reject";

    /**
     * 退回： 退回上一个节点 {@value}
     */
    public static final String ROLLBACK = "rollback";

}
