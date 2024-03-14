package com.xht.kettle.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.kettle.entity.BaseEntity;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author xht
 * @since 2024-03-11
 */
@Getter
@Setter
@TableName("kettle_file")
public class KettleFile extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 文件名
     */
    private String taskName;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 上传人
     */
    private String creator;

    /**
     * 文件描述
     */
    private String description;

    private String type;

}
