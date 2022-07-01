package com.scientific.audit.controller;


import com.scientific.audit.common.model.base.Result;
import com.scientific.audit.common.model.vo.EmailParamVo;
import com.scientific.audit.service.MailService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@Api(tags = "邮件功能")
public class EmailController{

    @Resource
    MailService mailService;


    @PostMapping("/api/sendEmail")
    @ResponseBody
    @Operation(summary = "注册验证码邮件发送")
    public Result sendEmail(@Validated @RequestBody EmailParamVo email) {
        mailService.sendMimeMail(email.getEmail());
        return Result.buildOK();

    }
}
