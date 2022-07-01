package com.scientific.audit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scientific.audit.common.model.entity.PaperFile;
import com.scientific.audit.common.model.vo.PaperOperationVo;
import com.scientific.audit.common.model.vo.PaperVo;
import com.scientific.audit.common.model.vo.SaveVo;

/**
 *
 */
public interface PaperFileService extends IService<PaperFile>{

    void saveFile(PaperVo paperVO);

    void saves(SaveVo saveVo);

    void updateFile(PaperOperationVo paperOperationVo);

}
