package com.xht.es.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;


/**
 * @author : YIYUANYUAN
 * @date: 2024/10/4  16:10
 * 确诊人
 */
@Data
@Document(indexName = "case_person")
public class CasePerson {
    @Field(type = FieldType.Date)
    private String date;
    @GeoPointField
    private Object location;
    @Field(type = FieldType.Keyword)
    private String name;
    @Field(type = FieldType.Keyword)
    private String status;
}
