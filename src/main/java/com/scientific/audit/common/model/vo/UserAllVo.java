package com.scientific.audit.common.model.vo;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
public class UserAllVo implements Serializable{
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 邮箱， 登录凭据
     */
    @ApiModelProperty("邮箱，登录凭据")
    @TableField(value = "email")
    private String email;


    /**
     * 性别； 0：女， 1：男
     */
    @ApiModelProperty("性别")
    @TableField(value = "gender")
    private Byte gender;

    /**
     * 用户类型，0：游客，1：学生，2：老师，3：管理员
     */
    @ApiModelProperty("用户类型")
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
    @TableField(value = "avatar_url", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String avatarUrl;

    /**
     * 用户状态；0：为激活，1：正常，2：锁定
     */
    @ApiModelProperty("用户状态")
    @TableField(value = "state")
    private Byte state;

    /**
     * 方向ID
     */
    @ApiModelProperty("方向ID")
    @TableField(value = "direction_id")
    private Long directionId;

    @ApiModelProperty("研究方向")
    @TableField(value = "direction_name")
    private String directionName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private String token;
}
