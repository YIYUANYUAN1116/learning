package com.xht.kettle.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "响应结果实体类")
public class R<T> {

    //返回码
    @Schema(description = "业务状态码")
    private Integer code;

    //返回消息
    @Schema(description = "响应消息")
    private String message;

    //返回数据
    @Schema(description = "业务数据")
    private T data;

    // 私有化构造
    private R() {}

    // 返回数据
    public static <T> R<T> build(T body, Integer code, String message) {
        R<T> r = new R<>();
        r.setData(body);
        r.setCode(code);
        r.setMessage(message);
        return r;
    }

    // 通过枚举构造Result对象
    public static <T> R build(T body , ResultCodeEnum resultCodeEnum) {
        return build(body , resultCodeEnum.getCode() , resultCodeEnum.getMessage()) ;
    }

    public static <T> R build(ResultCodeEnum resultCodeEnum) {
        return build(null , resultCodeEnum.getCode() , resultCodeEnum.getMessage()) ;
    }
}
