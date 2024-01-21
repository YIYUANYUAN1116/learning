package com.xht.es.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


/**
 * @author : YIYUANYUAN
 * @date: 2024/1/21  16:23
 */

@Data
@Document(indexName = "account")
public class Account {

    @Field(type = FieldType.Long)
    private Long account_number;

    @Field(type = FieldType.Keyword)
    private String address;

    @Field(type = FieldType.Long)
    private Long age;

    @Field(type = FieldType.Long)
    private Long balance;

    @Field(type = FieldType.Keyword)
    private String city;

    @Field(type = FieldType.Keyword)
    private String email;

    @Field(type = FieldType.Keyword)
    private String employer;

    @Field(type = FieldType.Keyword)
    private String firstname;

    @Field(type = FieldType.Keyword)
    private String gender;

    @Field(type = FieldType.Keyword)
    private String lastname;

    @Field(type = FieldType.Keyword)
    private String state;
}
