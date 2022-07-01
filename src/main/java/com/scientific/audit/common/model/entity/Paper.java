package com.scientific.audit.common.model.entity;

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
 * 论文表
 *
 * @TableName tb_paper
 */
@TableName(value = "tb_paper")
@Data
@ApiModel("论文表")
public class Paper implements Serializable{
    /**
     *
     */
    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 类型，0：论文，1：专利，2：报告
     */
    @ApiModelProperty("类型，0：论文，1：专利，2：报告")
    @TableField(value = "types")
    private Byte types;

    /**
     * 论文标题
     */
    @ApiModelProperty("论文标题")
    @TableField(value = "title")
    private String title;

    /**
     * 英文标题
     */
    @ApiModelProperty("英文标题")
    @TableField(value = "title_en")
    private String titleEn;

    /**
     * 论文摘要
     */
    @ApiModelProperty("论文摘要")
    @TableField(value = "summary")
    private String summary;

    /**
     * 英文摘要
     */
    @ApiModelProperty("英文摘要")
    @TableField(value = "summary_en")
    private String summaryEn;

    /**
     * 论文关键词
     */
    @ApiModelProperty("论文关键词")
    @TableField(value = "keyword")
    private String keyword;

    /**
     * 英文关键词
     */
    @ApiModelProperty("英文关键词")
    @TableField(value = "keyword_en")
    private String keywordEn;

    /**
     * 论文状态，0：未审核，1：初审通过，2：初审未通过，3：二审通过，4：二审未通过，5：归档，6：终审未通过；默认未通过
     */
    @ApiModelProperty("论文状态，0：未审核，1：初审通过，2：初审未通过，3：二审通过，4：二审未通过，5：归档，6：终审未通过；默认未通过")
    @TableField(value = "state")
    private Byte state;

    /**
     * 论文上传时间
     */
    @ApiModelProperty("论文上传时间")
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 上传者id
     */
    @ApiModelProperty("上传者ID")
    @TableField(value = "uploader_id")
    private Long uploaderId;

    /**
     * 论文上传时间
     */
    @ApiModelProperty("论文上传时间")
    @TableField(value = "update_time")
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}