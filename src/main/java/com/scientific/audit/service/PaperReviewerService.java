package com.scientific.audit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scientific.audit.common.model.entity.PaperReviewer;
import com.scientific.audit.common.model.vo.PaperVo;
import com.scientific.audit.common.model.vo.ChangeTeacherVo;

/**
 *
 */
public interface PaperReviewerService extends IService<PaperReviewer>{

    void saveReviewer(PaperVo paperVO);

    void changeTeacher(ChangeTeacherVo changeTeacherVo);

}
