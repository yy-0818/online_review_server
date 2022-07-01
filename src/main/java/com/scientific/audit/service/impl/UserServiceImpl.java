package com.scientific.audit.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scientific.audit.common.model.base.ResultCode;
import com.scientific.audit.common.model.entity.User;
import com.scientific.audit.common.model.vo.UserAllVo;
import com.scientific.audit.common.model.vo.UserTeacherVo;
import com.scientific.audit.common.model.vo.UserUpdateVo;
import com.scientific.audit.common.model.vo.UserVo;
import com.scientific.audit.common.util.AssertUtils;
import com.scientific.audit.mapper.UserMapper;
import com.scientific.audit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
@Transactional
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService{

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    UserService userService;
    @Resource
    UserMapper userMapper;

    @Override
    public User login(String email, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        queryWrapper.eq("password", SecureUtil.md5(password));
        return getOne(queryWrapper);
    }

    @Override
    public User queryByEmail(String email) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        return getOne(queryWrapper);
    }

    @Override
    public void register(UserVo userVo) {
        log.debug(userVo.toString());
        log.debug(userVo.toString());
        AssertUtils.assertTrue(validCode(userVo), ResultCode.INVALID_CAPTCHA);
        User user = queryByEmail(userVo.getEmail());
        AssertUtils.assertNull(user, ResultCode.USER_ACCOUNT_EXIST);
        User u = new User();
        BeanUtils.copyProperties(userVo, u);
        u.setPassword(SecureUtil.md5(u.getPassword()));
        save(u);
    }

    @Override
    public void changePassword(UserVo userVo) {
        AssertUtils.assertTrue(validCode(userVo), ResultCode.INVALID_CAPTCHA);
//        User u = new User();
//        BeanUtils.copyProperties(userVo, u);
//        u.setPassword(SecureUtil.md5(u.getPassword()));
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getEmail, userVo.getEmail());
        AssertUtils.assertNotNull(getOne(lambdaQueryWrapper),ResultCode.NOT_EXIST_USER);

        LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(User::getEmail,userVo.getEmail()).set(User::getPassword,SecureUtil.md5(userVo.getPassword()));
        update(lambdaUpdateWrapper);
    }

    @Override
    public boolean validCode(UserVo userVo) {
        //获取redis中的验证信息
        String savedCode = (String) redisTemplate.boundValueOps(userVo.getEmail() + "code").get();
        return userVo.getCode().equals(savedCode);
    }

    @Override
    public void update(UserUpdateVo userUpdateVo) {
        if (StrUtil.isNotBlank(userUpdateVo.getPassword())) {
            userUpdateVo.setPassword(SecureUtil.md5(userUpdateVo.getPassword()));
        }
        User user = new User();
        BeanUtils.copyProperties(userUpdateVo, user);
        userService.updateById(user);
    }

    @Override
    public Page<UserAllVo> getUserAllVO(Page<UserAllVo> page,String search) {
        return userMapper.getUserAllVo(page,search);
    }

    @Override
    public List<UserTeacherVo> getUserTeacher(List<Long> ids) {
        List<UserTeacherVo> userTeacherVoPage = userMapper.getUserByDirection(ids);
        System.out.println(userTeacherVoPage);
        List<UserTeacherVo> list2 = new ArrayList<>();
        for (UserTeacherVo userTeacherVo : userTeacherVoPage) {
            list2.add(userTeacherVo);
            System.out.println(userTeacherVo);
        }
        return list2;
    }

    @Override
    public List<User> findAllTeacher() {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getRole,3);
        List<User> list = list(lambdaQueryWrapper);
        return list;


    }

    @Override
    public List<User> firstTeacher() {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getRole,2);
        List<User> list = userMapper.selectList(lambdaQueryWrapper);
        return list;

    }

}




