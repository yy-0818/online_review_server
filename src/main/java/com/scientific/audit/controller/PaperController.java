package com.scientific.audit.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scientific.audit.common.model.base.Result;
import com.scientific.audit.common.model.entity.Paper;
import com.scientific.audit.common.model.entity.PaperReviewer;
import com.scientific.audit.common.model.entity.User;
import com.scientific.audit.common.model.vo.*;
import com.scientific.audit.service.PaperFileService;
import com.scientific.audit.service.PaperReviewerService;
import com.scientific.audit.service.PaperService;
import com.scientific.audit.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/paper")
@Api(tags = "论文接口")
@Slf4j
public class PaperController{

    @Resource
    PaperService paperService;
    @Resource
    PaperFileService paperFileService;
    @Resource
    PaperReviewerService paperReviewerService;
    @Resource
    UserService userService;




    @Operation(summary = "表单数据保存")
    @PostMapping("/save")
    public Result savaPaper(@RequestBody @Validated PaperVo paperVO) {
        System.out.println(paperVO);
        paperService.savaPaper(paperVO);
        return Result.buildOK();
    }

    @Operation(summary = "初审退回按钮接口")
    @PostMapping("/failBackFirst")
    public Result failBackFirst(@RequestBody PaperOperationVo paperOperationVo) {
        paperService.failBackFirst(paperOperationVo);
        return Result.buildOK();
    }

    @Operation(summary = "二审退回按钮接口")
    @PostMapping("/failBackSecond")
    public Result failBackSecond(@RequestBody PaperOperationVo paperOperationVo) {
        paperService.failBackSecond(paperOperationVo);
        return Result.buildOK();
    }

    @Operation(summary = "终审退回按钮接口")
    @PostMapping("/failBackThird")
    public Result failBackThird(@RequestBody PaperOperationVo paperOperationVo) {
        paperService.failBackThird(paperOperationVo);
        return Result.buildOK();
    }

    @Operation(summary = "初审通过按钮接口")
    @PostMapping("/passFirst")
    public Result passPrimary(@RequestBody PaperOperationVo paperOperationVo) {
        paperService.passFirst(paperOperationVo);
        return Result.buildOK();
    }

    @Operation(summary = "二审通过按钮接口")
    @PostMapping("/passSecond")
    public Result passSecond(@RequestBody PaperOperationVo paperOperationVo) {
        paperService.passSecond(paperOperationVo);
        return Result.buildOK();
    }

    @Operation(summary = "终审通过按钮接口")
    @PostMapping("/passThird")
    public Result passThird(@RequestBody PaperOperationVo paperOperationVo) {
        paperService.passThird(paperOperationVo);
        return Result.buildOK();
    }

    @Operation(summary = "重新上传文件")
    @PostMapping("saves")
    public Result saves(@RequestBody SaveVo saveVo) {
        paperFileService.saves(saveVo);
        return Result.buildOK();
    }

    @Operation(summary = "文章按类型查看，0：论文，1：专利，2：报告------所有东西")
    @GetMapping("/findByTypesAll")
    public Result findByTypesAll(@RequestParam(defaultValue = "10") Integer pageSize,
                                 @RequestParam(defaultValue = "1") Integer pageNum,
                                 @RequestParam(value = "types", required = false) int types,
                                 @RequestParam(required = false) String search) {

        log.info("id:{}", types);
        log.info("search : {}", search);
        IPage<FindByTypesVo> page = paperService.findByTypes(new Page<>(pageNum, pageSize), search, types);
        return Result.buildOK(page);
    }

    @Operation(summary = "文章按类型查看，0：论文，1：专利，2：报告------未审核和初审待修改数据")
    @GetMapping("/findByTypesFirst")
    public Result findByTypesFirst(@RequestParam(defaultValue = "10") Integer pageSize,
                                   @RequestParam(defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "types", required = false) int types,
                                   @RequestParam(required = false) String search) {

        log.info("id:{}", types);
        log.info("search : {}", search);
        IPage<FindByTypesVo> page = paperService.findByTypesFirst(new Page<>(pageNum, pageSize), search, types);
        return Result.buildOK(page);
    }

    @Operation(summary = "文章按类型查看，0：论文，1：专利，2：报告------初审通过和二审待修改")
    @GetMapping("/findByTypesSecond")
    public Result findByTypesSecond(@RequestParam(defaultValue = "10") Integer pageSize,
                                    @RequestParam(defaultValue = "1") Integer pageNum,
                                    @RequestParam(value = "types", required = false) int types,
                                    @RequestParam(required = false) String search) {

        log.info("id:{}", types);
        log.info("search : {}", search);
        IPage<FindByTypesVo> page = paperService.findByTypesSecond(new Page<>(pageNum, pageSize), search, types);
        return Result.buildOK(page);
    }

    @Operation(summary = "文章按类型查看，0：论文，1：专利，2：报告------二审通过和终审待修改")
    @GetMapping("/findByTypesThird")
    public Result findByTypesThird(@RequestParam(defaultValue = "10") Integer pageSize,
                                   @RequestParam(defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "types", required = false) int types,
                                   @RequestParam(required = false) String search) {

        log.info("id:{}", types);
        log.info("search : {}", search);
        IPage<FindByTypesVo> page = paperService.findByTypesThird(new Page<>(pageNum, pageSize), search, types);
        return Result.buildOK(page);
    }

    @Operation(summary = "文章按类型查看，0：论文，1：专利，2：报告------归档")
    @GetMapping("/findByTypesFourth")
    public Result findByTypesFourth(@RequestParam(defaultValue = "10") Integer pageSize,
                                    @RequestParam(defaultValue = "1") Integer pageNum,
//                                    @RequestParam(value = "types", required = false) int types,
                                    @RequestParam(required = false) String search) {

//        log.info("id:{}", types);
        log.info("search : {}", search);
        IPage<FindByTypesVo> page = paperService.findByTypesFourth(new Page<>(pageNum, pageSize), search);
        return Result.buildOK(page);
    }

    @Operation(summary = "关键字查询接口")
    @PostMapping("/suspectQuery")
    public Result suspectQuery(@RequestParam(defaultValue = "10") Integer pageSize,
                               @RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam String keyWords) {
        IPage<Paper> pape = paperService.suspectQuery(new Page<>(pageNum, pageSize), keyWords);
        return Result.buildOK(pape);
    }

    @Operation(summary = "学生查看自己上传的文章接口")
    @GetMapping("/student/{id}")
    public Result findByStudent(@RequestParam(defaultValue = "10") Integer pageSize,
                                @RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(required = false) String search,
                                @ApiParam(name = "id", value = "用户Id", required = true)
                                @PathVariable Long id

    ) {
        IPage<FindByTypesVo> page = paperService.findByStudent(new Page<>(pageNum, pageSize), search,id);
        return Result.buildOK(page);
    }

    @Operation(summary = "小红点")
    @PostMapping("/red")
    public Result red() {
        Map<String, CountVo> red = paperService.red();
        return Result.buildOK(red);
    }

    @Operation(summary = "更改审稿老师")
    @PostMapping("/changeTeacher")
    public Result changeTeacher(@RequestBody @Validated ChangeTeacherVo changeTeacherVo){
        paperReviewerService.changeTeacher(changeTeacherVo);
        return Result.buildOK();
    }

    @Operation(summary = "所有初审老师")
    @GetMapping("/firstTeacher")
    public Result firstTeacher(){
        List<User> list = userService.firstTeacher();
        return Result.buildOK(list);
    }


}
