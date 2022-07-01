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
 * @TableName tb_paper_file
 */
@TableName(value = "tb_paper_file")
@Data
@ApiModel("paper_file")
public class PaperFile implements Serializable{
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 论文Id
     */
    @ApiModelProperty("论文ID")
    @TableField(value = "paper_id")
    private Long paperId;

    /**
     * 文件地址
     */
    @ApiModelProperty("文件地址")
    @TableField(value = "url")
    private String url;

    /**
     * 文件类型， 0为需要审核的文件地址，1为老师意见
     */
    @ApiModelProperty("文件类型， 0为需要审核的文件地址，1为老师意见")
    @TableField(value = "type_or")
    private Byte typeOr;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}