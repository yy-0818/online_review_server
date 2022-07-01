package com.scientific.audit.common.model.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@ApiModel("用户模型")
public class UserVo{

    /**
     * 用户登录注册VO
     */


    @ApiModelProperty("用户邮箱")
    private String email;

    @ApiModelProperty("用户密码")
    @Length(min = 6, max = 16, message = "密码为6-16个字符")
    private String password;

    @ApiModelProperty("验证码")
    // 验证码
    private String code;

}
