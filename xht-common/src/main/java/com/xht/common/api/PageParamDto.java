package com.xht.common.api;

import lombok.Data;

/**
 * 功能描述
 *
 * @author dora
 */
@Data
public class PageParamDto<E> {

    /**
     * 当前页码
     */
    private Integer pageNum;

    /**
     * 每页数量
     */
    private Integer pageSize;

    /**
     * 正序
     */
    private String asc;

    /**
     * 倒序
     */
    private String desc;

    /**
     * 参数
     */
    private E param;

}
