package com.scientific.audit.common.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("更改老师")
public class ChangeTeacherVo{


    @ApiModelProperty("论文ID")
    private Long id;


    @ApiModelProperty("审稿人ID----字符串")
    private String reviewerId;
}
