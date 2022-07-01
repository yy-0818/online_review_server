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
 * @TableName tb_paper_direction
 */
@TableName(value = "tb_paper_direction")
@Data
@ApiModel("paper_direction")
public class PaperDirection implements Serializable{
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
     * 方向Id
     */
    @ApiModelProperty("方向id")
    @TableField(value = "direction_id")
    private Long directionId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}