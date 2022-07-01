package com.scientific.audit.common.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName tb_paper_reviewer
 */
@TableName(value = "tb_paper_reviewer")
@Data
@ApiModel("paper_reviewer")
public class PaperReviewer implements Serializable{
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
     * 审稿人Id
     */
    @ApiModelProperty("审稿人ID")
    @TableField(value = "user_id")
    private Long userId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}