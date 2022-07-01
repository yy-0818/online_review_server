package com.scientific.audit.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scientific.audit.common.model.entity.User;
import com.scientific.audit.common.model.vo.UserAllVo;
import com.scientific.audit.common.model.vo.UserTeacherVo;
import com.scientific.audit.common.model.vo.UserUpdateVo;
import com.scientific.audit.common.model.vo.UserVo;

import java.util.List;

/**
 *
 */
public interface UserService extends IService<User>{

    User login(String email, String password);

    User queryByEmail(String email);

    void register(UserVo userVo);

    void changePassword(UserVo userVo);

    boolean validCode(UserVo userVo);

    void update(UserUpdateVo userUpdateVo);

    Page<UserAllVo> getUserAllVO(Page<UserAllVo> page,String search);

    List<UserTeacherVo> getUserTeacher(List<Long> ids);

    List<User> findAllTeacher();

    List<User> firstTeacher();

}
