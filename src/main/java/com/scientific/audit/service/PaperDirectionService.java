package com.scientific.audit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scientific.audit.common.model.entity.PaperDirection;
import com.scientific.audit.common.model.vo.PaperVo;

/**
 *
 */
public interface PaperDirectionService extends IService<PaperDirection>{

    void savaDirection(PaperVo paperVO);


}
