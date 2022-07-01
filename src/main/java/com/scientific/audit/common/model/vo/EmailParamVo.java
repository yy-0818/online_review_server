package com.scientific.audit.common.model.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@Schema(name = "邮箱注册VO")
public class EmailParamVo{
    @Schema(name = "邮箱")
    @Email(message = "邮箱格式错误")
    @NotEmpty(message = "邮箱不能为空")
    private String email;
}
