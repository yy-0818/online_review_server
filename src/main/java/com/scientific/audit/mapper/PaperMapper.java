package com.scientific.audit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scientific.audit.common.model.entity.Paper;
import com.scientific.audit.common.model.vo.CountVo;
import com.scientific.audit.common.model.vo.FindByTypesVo;
import com.scientific.audit.common.model.vo.PaperVo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Entity com.scientific.audit.common.model.entity.Paper
 */
public interface PaperMapper extends BaseMapper<Paper>{

    List<PaperVo> selectTeacherEmails(@Param("id") Long id);

    IPage<FindByTypesVo> findByTypes(Page<?> page, @Param("search") String search, @Param("types") int types);

    IPage<FindByTypesVo> findByTypesFirst(Page<?> page, @Param("search") String search, @Param("types") int types);

    IPage<FindByTypesVo> findByTypesSecond(Page<?> page, @Param("search") String search, @Param("types") int types);

    IPage<FindByTypesVo> findByTypesThird(Page<?> page, @Param("search") String search, @Param("types") int types);

    IPage<FindByTypesVo> findByTypesFourth(Page<?> page, @Param("search") String search);

    IPage<FindByTypesVo> findByStudent(Page<?> page, @Param("search") String search, @Param("id") Long id);

    @MapKey(value = "state")
    Map<String, CountVo> countByStateAndType();


    List<Paper> scheduledTaskStudent();

    List<Paper> scheduledTaskTeacher();

}





