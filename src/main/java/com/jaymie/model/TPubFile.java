package com.jaymie.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 附件信息表
 * </p>
 *
 * @author 
 * @since 2019-04-17
 */
@Data
@Accessors(chain = true)
@TableName("t_pub_file")
public class TPubFile implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private String id;

    private int lid;

    /**
     * 文件类型，0图片 1视频
     */
    @TableField("file_type")
    private String fileType;

    /**
     * 文件原名称
     */
    @TableField("source_name")
    private String sourceName;

    /**
     * 服务器上存放名称
     */
    @TableField("re_name")
    private String reName;

    /**
     * 网络URL路径
     */
    @TableField("file_url")
    private String fileUrl;

    /**
     * 文件后缀
     */
    @TableField("file_suffix")
    private String fileSuffix;

    /**
     * 文件标识
     */
    private String flag;

    /**
     * 上传时间
     */
    @TableField("event_time")
    private String eventTime;

    /**
     * 备用字段1
     */
    private String temp1;

    /**
     * 备用字段2
     */
    private String temp2;

    /**
     * 备用字段3
     */
    private String temp3;

    /**
     * 备用字段4
     */
    private String temp4;


}
