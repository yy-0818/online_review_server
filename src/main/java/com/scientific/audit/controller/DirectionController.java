package com.scientific.audit.controller;

import com.scientific.audit.common.model.base.Result;
import com.scientific.audit.service.DirectionService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/direction")
@Api(tags = "论文方向")
public class DirectionController{


    @Resource
    DirectionService directionService;

    @GetMapping
    public Result direction() {
        return Result.buildOK(directionService.list());
    }


}
