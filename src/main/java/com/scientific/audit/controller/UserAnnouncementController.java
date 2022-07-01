package com.scientific.audit.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scientific.audit.common.model.base.Result;
import com.scientific.audit.common.model.entity.News;
import com.scientific.audit.common.model.entity.UserAnnouncement;
import com.scientific.audit.common.model.vo.UserAnnouncementVo;
import com.scientific.audit.mapper.UserAnnouncementMapper;
import com.scientific.audit.service.UserAnnouncementService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;



@RestController
@RequestMapping("/announcement")
@Api(tags = "公告接口")
@Slf4j
public class UserAnnouncementController{

    @Resource
    UserAnnouncementService userAnnouncementService;
    @Resource
    UserAnnouncementMapper userAnnouncementMapper;


    @Operation(summary = "保存公告")
    @PostMapping("/save")
    public Result saveUserAnnouncement(@RequestBody UserAnnouncement userAnnouncement){
        userAnnouncementService.saveUserAnnouncement(userAnnouncement);
        return Result.buildOK();
    }

    @Operation(summary = "删除公告")
    @DeleteMapping("/{id}")
    public Result deleteUserAnnouncement(@PathVariable Long id){
        userAnnouncementService.removeById(id);
        return Result.buildOK();
    }

    @Operation(summary = "更新公告")
    @PostMapping("/update")
    public Result updateUserAnnouncement(@RequestBody UserAnnouncement userAnnouncement){
        userAnnouncementMapper.updateById(userAnnouncement);
        return Result.buildOK();
    }

    @Operation(summary = "查询所有")
    @GetMapping("/findAll")
    public Result findAllUserAnnouncements(@RequestParam(defaultValue = "10") Integer pageSize,
                                           @RequestParam(defaultValue = "1") Integer pageNum,
                                           @RequestParam(required = false) String search
                                           ){
        IPage<UserAnnouncementVo> page = userAnnouncementService.findUserAnnounce(new Page<>(pageNum, pageSize),search);
        return Result.buildOK(page);
    }
}
