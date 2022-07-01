package com.scientific.audit.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scientific.audit.common.model.entity.Paper;
import com.scientific.audit.common.model.vo.*;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface PaperService extends IService<Paper>{

    void savaPaper(PaperVo paperVO);

    void saves(SaveVo saveVo);

    void failBackFirst(PaperOperationVo paperOperationVo);

    void failBackSecond(PaperOperationVo paperOperationVo);

    void failBackThird(PaperOperationVo paperOperationVo);

    void passFirst(PaperOperationVo paperOperationVo);

    void passSecond(PaperOperationVo paperOperationVo);

    void passThird(PaperOperationVo paperOperationVo);

    IPage<FindByTypesVo> findByTypes(Page<FindByTypesVo> page, String search, int types);

    IPage<FindByTypesVo> findByTypesFirst(Page<FindByTypesVo> page, String search, int types);

    IPage<FindByTypesVo> findByTypesSecond(Page<FindByTypesVo> page, String search, int types);

    IPage<FindByTypesVo> findByTypesThird(Page<FindByTypesVo> page, String search, int types);

    IPage<FindByTypesVo> findByTypesFourth(Page<FindByTypesVo> page, String search);

    IPage<Paper> suspectQuery(Page<Paper> page, String keyWords);

    IPage<FindByTypesVo> findByStudent(Page<FindByTypesVo> page, String search,Long id);

    Map<String, CountVo> red();


}
