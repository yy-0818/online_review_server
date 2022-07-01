package com.scientific.audit.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scientific.audit.common.model.entity.User;
import com.scientific.audit.common.model.entity.UserAnnouncement;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scientific.audit.common.model.vo.FindByTypesVo;
import com.scientific.audit.common.model.vo.UserAnnouncementVo;

/**
 *
 */
public interface UserAnnouncementService extends IService<UserAnnouncement> {

    void saveUserAnnouncement (UserAnnouncement userAnnouncement);

    void remove(Long id);

    void update(UserAnnouncement userAnnouncement);

    IPage<UserAnnouncementVo> findUserAnnounce(Page<UserAnnouncementVo> page, String search);


}
