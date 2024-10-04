package com.xht.es.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.*;


/**
 * @author : YIYUANYUAN
 * @date: 2024/10/4  16:08
 * 医院
 */

@Data
@Document(indexName = "hospital")
public class Hospital {
    @Field(type = FieldType.Keyword)
    private String address;
    @Field(type = FieldType.Keyword)
    private String district;
    @GeoPointField
    private Object location;
    @Field(type = FieldType.Keyword)
    private String lv;
    @Field(type = FieldType.Keyword)
    private String name;
}
