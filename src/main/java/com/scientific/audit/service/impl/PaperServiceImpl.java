package com.scientific.audit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scientific.audit.common.model.entity.Paper;
import com.scientific.audit.common.model.entity.PaperFile;
import com.scientific.audit.common.model.entity.User;
import com.scientific.audit.common.model.vo.*;
import com.scientific.audit.mapper.PaperFileMapper;
import com.scientific.audit.mapper.PaperMapper;
import com.scientific.audit.mapper.UserMapper;
import com.scientific.audit.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class PaperServiceImpl extends ServiceImpl<PaperMapper, Paper>
        implements PaperService{

    @Resource
    PaperDirectionService paperDirectionService;
    @Resource
    PaperFileService paperFileService;
    @Resource
    PaperReviewerService paperReviewerService;
    @Resource
    UserService userService;
    @Resource
    MailService mailService;
    @Resource
    PaperMapper paperMapper;
    @Resource
    UserMapper userMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void savaPaper(PaperVo paperVO) {
        Paper paper = new Paper();

        BeanUtils.copyProperties(paperVO, paper);
        log.debug("paper: {}", paper);
        save(paper);  //Paper表单保存
        paperVO.setId(paper.getId());
        //保存论文对应的多个研究方向
        paperFileService.saveFile(paperVO);
        System.out.println(paperVO);
        paperDirectionService.savaDirection(paperVO);
        System.out.println(paperVO);
        paperReviewerService.saveReviewer(paperVO);


    }

    @Override
    public void saves(SaveVo saveVo) {
        LambdaUpdateWrapper<Paper> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Paper::getId,saveVo.getId());
        Paper one = getOne(lambdaUpdateWrapper);
        if (one.getState()==2){
            lambdaUpdateWrapper.set(Paper::getState,0);
        }else if (one.getState()==4){
            lambdaUpdateWrapper.set(Paper::getState,1);
        }else if (one.getState()==6){
            lambdaUpdateWrapper.set(Paper::getState,3);
        }
        update(null,lambdaUpdateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void failBackFirst(PaperOperationVo paperOperationVo) {
        paperFileService.updateFile(paperOperationVo);
        Paper paper = getById(paperOperationVo.getId());
        Long uploaderId = paper.getUploaderId();
        User user = userService.getById(uploaderId);
        String email = user.getEmail();
        String title = "论文初审未通过";
        mailService.failBackFirst(email, paperOperationVo, paper,title);
        LambdaUpdateWrapper<Paper> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Paper::getId, paper.getId()).set(Paper::getState, 2);
        update(null, lambdaUpdateWrapper);
    }

    @Override
    public void failBackSecond(PaperOperationVo paperOperationVo) {
        paperFileService.updateFile(paperOperationVo);
        Paper paper = getById(paperOperationVo.getId());
        Long uploaderId = paper.getUploaderId();
        User user = userService.getById(uploaderId);
        String email = user.getEmail();
        String title = "论文二审未通过";
        mailService.failBackFirst(email, paperOperationVo, paper,title);
        LambdaUpdateWrapper<Paper> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Paper::getId, paper.getId()).set(Paper::getState, 4);
        update(null, lambdaUpdateWrapper);
    }

    @Override
    public void failBackThird(PaperOperationVo paperOperationVo) {
        paperFileService.updateFile(paperOperationVo);
        Paper paper = getById(paperOperationVo.getId());
        Long uploaderId = paper.getUploaderId();
        User user = userService.getById(uploaderId);
        String email = user.getEmail();
        String title = "论文终审未通过";
        mailService.failBackFirst(email, paperOperationVo, paper,title);
        LambdaUpdateWrapper<Paper> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Paper::getId, paper.getId()).set(Paper::getState, 6);
        update(null, lambdaUpdateWrapper);
    }


    @Override
    public void passFirst(PaperOperationVo paperOperationVo) {
        Paper paper = getById(paperOperationVo.getId());
        LambdaUpdateWrapper<Paper> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        Long uploaderId = paper.getUploaderId();
        User user = userService.getById(uploaderId);
        String email = user.getEmail();
        if (StringUtils.isNotBlank(paperOperationVo.getCommentFileUrl())) {
            //发送待修改邮件，并修改论文状态文：2（初审待修改）
            mailService.toBeModified(email, paperOperationVo, paper);
            lambdaUpdateWrapper.eq(Paper::getId, paper.getId()).set(Paper::getState, 2);
            update(null, lambdaUpdateWrapper);

            //保存老师批注文件
            paperFileService.updateFile(paperOperationVo);
        } else {
            //初审通过给学生发邮箱，并更改论文状态为：1（初审通过）
            mailService.passFirst(email, paperOperationVo, paper);
            lambdaUpdateWrapper.eq(Paper::getId, paper.getId()).set(Paper::getState, 1);
            update(null, lambdaUpdateWrapper);

            //给学生选择的老师群发邮件通知审核
            List<PaperVo> list = paperMapper.selectTeacherEmails(paperOperationVo.getId());
            System.out.println(list);
            List<String> teacherEmails = new ArrayList<>();
            for (PaperVo paperVo : list) {
                teacherEmails.add(paperVo.getEmail());
            }
            mailService.passToTeacher(teacherEmails);
        }


    }

    @Override
    public void passSecond(PaperOperationVo paperOperationVo) {
        Paper paper = getById(paperOperationVo.getId());
        LambdaUpdateWrapper<Paper> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        Long uploaderId = paper.getUploaderId();
        User user = userService.getById(uploaderId);
        String email = user.getEmail();
        if (!(StringUtils.isNotBlank(paperOperationVo.getCommentFileUrl()))) {
            //初审通过给学生发邮箱，并更改论文状态为：3（二审通过）
            mailService.passSecond(email, paperOperationVo, paper);
            lambdaUpdateWrapper.eq(Paper::getId, paper.getId()).set(Paper::getState, 3);

            //给学生选择的老师群发邮件通知审核
            List<String> teacherEmails = new ArrayList<>();
            LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(User::getRole, 4);
            List<User> users = userMapper.selectList(lambdaQueryWrapper);
            for (User user1 : users) {
                teacherEmails.add(user1.getEmail());
            }
            mailService.passToTeacher(teacherEmails);
        } else {
            //发送待修改邮件，并修改论文状态文：4（二审待修改）
            mailService.toBeModified(email, paperOperationVo, paper);
            lambdaUpdateWrapper.eq(Paper::getId, paper.getId()).set(Paper::getState, 4);

            //更新老师批注文件
            paperFileService.updateFile(paperOperationVo);

        }
        update(null, lambdaUpdateWrapper);
    }

    @Override
    public void passThird(PaperOperationVo paperOperationVo) {
        Paper paper = getById(paperOperationVo.getId());
        LambdaUpdateWrapper<Paper> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        Long uploaderId = paper.getUploaderId();
        User user = userService.getById(uploaderId);
        String email = user.getEmail();
        if (!(StringUtils.isNotBlank(paperOperationVo.getCommentFileUrl()))) {
            lambdaUpdateWrapper.eq(Paper::getId, paper.getId()).set(Paper::getState, 5);
            mailService.passThird(email, paperOperationVo, paper);
        } else {
            mailService.toBeModified(email, paperOperationVo, paper);
            lambdaUpdateWrapper.eq(Paper::getId, paper.getId()).set(Paper::getState, 6);

            //更新老师批注文件
            paperFileService.updateFile(paperOperationVo);
        }
        update(null, lambdaUpdateWrapper);

    }

    @Override
    public IPage<FindByTypesVo> findByTypes(Page<FindByTypesVo> page, String search, int types) {
        return paperMapper.findByTypes(page, search, types);
    }

    @Override
    public IPage<FindByTypesVo> findByTypesFirst(Page<FindByTypesVo> page, String search, int types) {
        return paperMapper.findByTypesFirst(page, search, types);
    }

    @Override
    public IPage<FindByTypesVo> findByTypesSecond(Page<FindByTypesVo> page, String search, int types) {

        return paperMapper.findByTypesSecond(page, search, types);
    }

    @Override
    public IPage<FindByTypesVo> findByTypesThird(Page<FindByTypesVo> page, String search, int types) {
        return paperMapper.findByTypesThird(page, search, types);
    }

    @Override
    public IPage<FindByTypesVo> findByTypesFourth(Page<FindByTypesVo> page, String search) {
        return paperMapper.findByTypesFourth(page, search);
    }

    @Override
    public IPage<Paper> suspectQuery(Page<Paper> page, String keyWords) {
        LambdaQueryWrapper<Paper> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(Paper::getTitle, keyWords)
                .or().like(Paper::getSummary, keyWords)
                .or().like(Paper::getKeyword, keyWords)
                .or().like(Paper::getTitleEn, keyWords)
                .or().like(Paper::getSummaryEn, keyWords)
                .or().like(Paper::getKeywordEn, keyWords);
        return page(page, lambdaQueryWrapper);
    }

    @Override
    public IPage<FindByTypesVo> findByStudent(Page<FindByTypesVo> page, String search,Long id) {
        return paperMapper.findByStudent(page, search,id);
    }

    @Override
    public Map<String, CountVo> red() {
        return paperMapper.countByStateAndType();
    }



}




