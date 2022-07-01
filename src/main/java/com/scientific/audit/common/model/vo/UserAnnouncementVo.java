package com.scientific.audit.common.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName tb_user_announcement
 */
@Data
@ApiModel("公告信息表Vo")
public class UserAnnouncementVo implements Serializable {
    /**
     * 
     */
    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 公告标题
     */
    @ApiModelProperty("公告标题")
    @TableField(value = "title")
    private String title;

    /**
     * 公告内容
     */
    @ApiModelProperty("公告内容")
    @TableField(value = "content")
    private String content;

    /**
     * 公告上传时间
     */
    @ApiModelProperty("公告上传时间")
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 上传者id
     */
    @ApiModelProperty("上传者id")
    @TableField(value = "uploader_id")
    private Long uploaderId;

    /**
     * 上传者id
     */
    @ApiModelProperty("图片地址")
    @TableField(value = "img_url")
    private String imgUrl;

    /**
     * 用户真实姓名
     */
    @ApiModelProperty("用户真实姓名")
    @TableField(value = "name")
    private String name;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}