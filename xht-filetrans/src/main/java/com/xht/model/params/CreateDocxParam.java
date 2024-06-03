package com.xht.model.params;

import lombok.Data;

@Data
public class CreateDocxParam {
    private String tempDocxPath;
    private String targetDocxPath;
    private Object data;
    private String dataClassName; //data 对象的全类名，需file服务能根据全类名取到class
}
