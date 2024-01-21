package com.xht.rabbitmq.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author : YIYUANYUAN
 * @description :
 * @date: 2024/1/5  10:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private BigDecimal money;
}
