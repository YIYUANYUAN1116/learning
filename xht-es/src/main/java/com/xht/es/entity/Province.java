package com.xht.es.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoShapeField;

/**
 * @author : YIYUANYUAN
 * @date: 2024/10/4  16:10、
 * 地区
 */

@Data
@Document(indexName = "province_bak")
public class Province {
    @Field(type = FieldType.Keyword)
    private String code;
    @GeoShapeField
    private Object location;
    @Field(type = FieldType.Keyword)
    private String name;
}
