package com.scientific.audit.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scientific.audit.common.model.base.Result;
import com.scientific.audit.common.model.base.ResultCode;
import com.scientific.audit.common.model.entity.User;
import com.scientific.audit.common.model.vo.UserAllVo;
import com.scientific.audit.common.model.vo.UserTeacherVo;
import com.scientific.audit.common.model.vo.UserUpdateVo;
import com.scientific.audit.common.model.vo.UserVo;
import com.scientific.audit.common.util.RedisUtils;
import com.scientific.audit.common.util.TokenUtils;
import com.scientific.audit.mapper.UserMapper;
import com.scientific.audit.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
@Api(tags = "用户接口")
public class UserController{

    @Resource
    UserService userService;
    @Resource
    TokenUtils tokenUtils;
    @Resource
    RedisUtils redisUtils;


    @Operation(summary = "登录")
    @PostMapping("/login")
    public Result login(@RequestBody UserVo userVo) {

        String keyLoginFail = "user" + userVo.getEmail() + ":login:fail:count";//用户登录次数
        String keyLoginStop = "user" + userVo.getEmail() + ":login:stop:time";//用户禁止登陆时间
        int num = 5;
        User res = null;
        if (redisUtils.hasKey(keyLoginStop)) {
            return Result.build(ResultCode.USER_FROZEN);
        } else {
            res = userService.login(userVo.getEmail(), userVo.getPassword());

            if (res == null) {

                if (!redisUtils.hasKey(keyLoginFail)) {
                    redisUtils.set(keyLoginFail, 1, 300);
                } else {
                    int failCount = (int) redisUtils.get(keyLoginFail);
                    if (failCount >= num - 1) {
                        redisUtils.set(keyLoginStop, 1, 1800);
                        return Result.build(ResultCode.NOT_EXIST_USER_OR_ERROR_PWD_AND_MAX_RETRY);
                    } else {
                        redisUtils.incr(keyLoginFail, 1);
                    }
                }
                return Result.build(ResultCode.NOT_EXIST_USER_OR_ERROR_PWD);
            }
        }
        // 生成token
        String token = tokenUtils.genToken(res);
        res.setToken(token);
        res.setPassword(null);
        return Result.buildOK(res);
    }


    @Operation(summary = "注册")
    @PostMapping("/register")
    public Result register(@RequestBody @Validated UserVo userVo) {
        userService.register(userVo);
        return Result.buildOK();
    }

    @Operation(summary = "用户信息更新")
    @PutMapping
    public Result update(@RequestBody UserUpdateVo userUpdateVo) {
        User user = new User();
        BeanUtils.copyProperties(userUpdateVo, user);
        if (StrUtil.isNotBlank(userUpdateVo.getPassword())) {
            user.setPassword(SecureUtil.md5(userUpdateVo.getPassword()));
        }
        else {
            user.setPassword(null);
        }

        log.debug("user: {}", user);
        userService.updateById(user);
        return Result.ok();
    }

    @Operation(summary = "删除")
    @DeleteMapping("/{id}")
    public Result update(@PathVariable Long id) {
        userService.removeById(id);
        return Result.ok();
    }

    @Operation(summary = "按用户ID查询")
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        User user = userService.getById(id);
        user.setPassword(null);
        return Result.ok(user);
    }

    @Operation(summary = "分页查询")
    @GetMapping("/all")
    public Result findAll(
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(required = false) String search) {

//        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        lambdaQueryWrapper.like(StrUtil.isNotBlank(search), User::getName, search);
        log.info("search : {}", search);
        Page<UserAllVo> page = userService.getUserAllVO(new Page<>(pageNum, pageSize),search);
        return Result.ok(page);
    }

    @Operation(summary = "查询身份")
    @PostMapping("/identity")
    public Result identity(@RequestBody List<Long> ids) {
        if (ids.isEmpty()) {
            return null;
        } else {
            List<UserTeacherVo> user = userService.getUserTeacher(ids);
            return Result.buildOK(user);
        }
    }

    @Operation(summary = "重置密码")
    @PostMapping("/changePassword")
    public Result changePassword(@RequestBody UserVo userVo){
        userService.changePassword(userVo);
        return Result.buildOK();
    }

    @Operation(summary = "找到所有老师")
    @PostMapping("/findAllTeacher")
    public Result findAllTeacher(){
        List<User> allTeacher = userService.findAllTeacher();
        return Result.buildOK(allTeacher);
    }


}
