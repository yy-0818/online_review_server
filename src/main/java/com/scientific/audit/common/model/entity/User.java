package com.scientific.audit.common.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户表
 *
 * @TableName tb_user
 */
@TableName(value = "tb_user")
@Data
@ApiModel("用户表")
public class User implements Serializable{
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 邮箱， 登录凭据
     */
    @ApiModelProperty("邮箱")
    @TableField(value = "email")
    private String email;

    /**
     * 密码， 登录凭据
     */
    @JsonIgnore
    @ApiModelProperty("密码")
    @TableField(value = "password")
    private String password;

    /**
     * 性别； 0：女， 1：男
     */
    @ApiModelProperty("性别：0：女， 1：男")
    @TableField(value = "gender")
    private Byte gender;

    /**
     * 用户类型，1：普通用户，2：老师，3：管理员,4：超级管理员
     */
    @ApiModelProperty("用户类型。 1：普通用户，2：老师，3：管理员,4：超级管理员")
    @TableField(value = "role")
    private Byte role;

    /**
     * 用户真实姓名
     */
    @ApiModelProperty("用户真实姓名")
    @TableField(value = "name")
    private String name;

    /**
     * 用户头像地址
     */
    @ApiModelProperty("用户头像地址")
    @TableField(value = "avatar_url")
    private String avatarUrl;

    /**
     * 用户状态；0：未激活，1：正常，2：锁定
     */
    @ApiModelProperty("用户状态；0：未激活，1：正常，2：锁定")
    @TableField(value = "state")
    private Byte state;

    /**
     * 方向ID
     */
    @ApiModelProperty("方向ID")
    @TableField(value = "direction_id")
    private Long directionId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private String token;
}