package com.scientific.audit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scientific.audit.common.model.entity.User;
import com.scientific.audit.common.model.entity.UserAnnouncement;
import com.scientific.audit.common.model.vo.FindByTypesVo;
import com.scientific.audit.common.model.vo.UserAnnouncementVo;
import com.scientific.audit.service.UserAnnouncementService;
import com.scientific.audit.mapper.UserAnnouncementMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@Service
public class UserAnnouncementServiceImpl extends ServiceImpl<UserAnnouncementMapper, UserAnnouncement>
    implements UserAnnouncementService{

    @Resource
    UserAnnouncementMapper userAnnouncementMapper;


    @Override
    public void saveUserAnnouncement(UserAnnouncement userAnnouncement) {
        save(userAnnouncement);
    }

    @Override
    public void remove(Long id) {
        removeById(id);
    }

    @Override
    public void update(UserAnnouncement userAnnouncement) {
        LambdaUpdateWrapper<UserAnnouncement> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(UserAnnouncement::getId,userAnnouncement.getId()).set(UserAnnouncement::getContent,userAnnouncement.getContent());

    }

    @Override
    public IPage<UserAnnouncementVo> findUserAnnounce(Page<UserAnnouncementVo> page, String search) {
        return userAnnouncementMapper.findUserAnnounce(page, search);
    }
}




