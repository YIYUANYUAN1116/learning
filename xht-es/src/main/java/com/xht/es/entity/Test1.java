package com.xht.es.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author : YIYUANYUAN
 * @date: 2024/1/21  17:40
 */

@Data
@Document(indexName = "test")
public class Test1 {
    private String name;
}
