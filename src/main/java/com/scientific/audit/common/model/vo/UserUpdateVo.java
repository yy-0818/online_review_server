package com.scientific.audit.common.model.vo;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
@ApiModel("用户更新信息")
public class UserUpdateVo{

    private Long id;

    /**
     * 性别； 0：女， 1：男
     */
    @ApiModelProperty("性别")
    @TableField(value = "gender")
    private Byte gender;

    /**
     * 用户真实姓名
     */
    @ApiModelProperty("用户真实姓名")
    @TableField(value = "name")
    private String name;

    /**
     * 密码， 登录凭据
     */
    @ApiModelProperty("密码，登录凭据")
    @TableField(value = "password", updateStrategy = FieldStrategy.NOT_EMPTY)
    @NotEmpty(message = "密码不能为空")
    @Length(min = 6, max = 16, message = "密码长度大于6且小于16")
    private String password;

    /**
     * 论文方向
     */
    @ApiModelProperty("论文方向")
    @TableField(value = "direction_id")
    private Long directionId;


    /**
     * 用户头像地址
     */
    @ApiModelProperty("用户头像地址")
    @TableField(value = "avatar_url")
    private String avatarUrl;

    /**
     * 用户类型，1：普通用户，2：老师，3：管理员,4：超级管理员
     */
    @ApiModelProperty("用户类型。 1：普通用户，2：老师，3：管理员,4：超级管理员")
    @TableField(value = "role")
    private Byte role;
}
