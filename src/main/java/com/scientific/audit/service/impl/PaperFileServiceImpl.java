package com.scientific.audit.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scientific.audit.common.model.entity.Paper;
import com.scientific.audit.common.model.entity.PaperFile;
import com.scientific.audit.common.model.vo.PaperOperationVo;
import com.scientific.audit.common.model.vo.PaperVo;
import com.scientific.audit.common.model.vo.SaveVo;
import com.scientific.audit.mapper.PaperFileMapper;
import com.scientific.audit.service.PaperFileService;
import com.scientific.audit.service.PaperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 */
@Service
@Slf4j
public class PaperFileServiceImpl extends ServiceImpl<PaperFileMapper, PaperFile>
        implements PaperFileService{

    @Resource
    PaperService paperService;


    @Override
    public void saveFile(PaperVo paperVO) {
        PaperFile paperFile = new PaperFile();
        paperFile.setUrl(paperVO.getUrl());
        paperFile.setPaperId(paperVO.getId());

        //需要审核的文件代号
        byte a = 0;
        paperFile.setTypeOr(a);
        log.debug("paperFile: {}", paperFile);
        save(paperFile);
    }


    @Override
    public void saves(SaveVo saveVo) {
        LambdaUpdateWrapper<PaperFile> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(PaperFile::getPaperId, saveVo.getId()).eq(PaperFile::getTypeOr, 0)
                .set(PaperFile::getUrl,saveVo.getUrl());
        update(null, lambdaUpdateWrapper);
        paperService.saves(saveVo);

    }

    @Override
    public void updateFile(PaperOperationVo paperOperationVo) {
        LambdaQueryWrapper<PaperFile> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(PaperFile::getPaperId, paperOperationVo.getId()).eq(PaperFile::getTypeOr, 1);
        PaperFile one = getOne(lambdaQueryWrapper);
        if (ObjectUtil.isNotNull(one)) {
            one.setUrl(paperOperationVo.getCommentFileUrl());
            saveOrUpdate(one);
        } else {
            PaperFile paperFile = new PaperFile();
            paperFile.setUrl(paperOperationVo.getCommentFileUrl());
            byte a = 1;
            paperFile.setTypeOr(a);
            paperFile.setPaperId(paperOperationVo.getId());
            save(paperFile);
        }


    }
}




