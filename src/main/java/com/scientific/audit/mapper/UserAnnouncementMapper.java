package com.scientific.audit.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scientific.audit.common.model.entity.UserAnnouncement;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scientific.audit.common.model.vo.FindByTypesVo;
import com.scientific.audit.common.model.vo.UserAnnouncementVo;
import org.apache.ibatis.annotations.Param;

/**
 * @Entity com.scientific.audit.common.model.entity.UserAnnouncement
 */
public interface UserAnnouncementMapper extends BaseMapper<UserAnnouncement> {

    IPage<UserAnnouncementVo> findUserAnnounce(Page<?> page, @Param("search") String search);
}




