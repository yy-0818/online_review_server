package com.scientific.audit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scientific.audit.common.model.entity.User;
import com.scientific.audit.common.model.vo.UserAllVo;
import com.scientific.audit.common.model.vo.UserTeacherVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Entity com.scientific.audit.common.model.entity.User
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User>{
    Page<UserAllVo> getUserAllVo(Page<?> page,@Param("search") String search);

    List<UserTeacherVo> getUserByDirection(@Param("ids") List<Long> ids);

}




