package com.scientific.audit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scientific.audit.common.model.entity.PaperReviewer;
import com.scientific.audit.common.model.entity.User;
import com.scientific.audit.common.model.vo.PaperVo;
import com.scientific.audit.common.model.vo.ChangeTeacherVo;
import com.scientific.audit.mapper.PaperReviewerMapper;
import com.scientific.audit.mapper.UserMapper;
import com.scientific.audit.service.PaperReviewerService;
import com.scientific.audit.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
@Service
public class PaperReviewerServiceImpl extends ServiceImpl<PaperReviewerMapper, PaperReviewer>
        implements PaperReviewerService{

    @Resource
    PaperReviewerMapper paperReviewerMapper;
    @Resource
    UserMapper userMapper;


    @Override
    public void saveReviewer(PaperVo paperVO) {
        PaperReviewer paperReviewer = new PaperReviewer();
        String reviewer = paperVO.getReviewerId();
        List<String> reviewerList = Arrays.asList(reviewer.split(","));
        System.out.println(reviewerList);
        int a = reviewerList.size();
        paperReviewer.setPaperId(paperVO.getId());
        for (int i = 0; i < a; i++) {
            long l = Long.parseLong(reviewerList.get(i));
            paperReviewer.setUserId(l);
            paperReviewer.setPaperId(paperVO.getId());
            paperReviewer.setId(null);
            save(paperReviewer);
        }

    }

    @Override
    public void changeTeacher(ChangeTeacherVo changeTeacherVo) {
        LambdaQueryWrapper<PaperReviewer> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(PaperReviewer::getPaperId,changeTeacherVo.getId());
        List<PaperReviewer> list = paperReviewerMapper.selectList(lambdaQueryWrapper);

        for (PaperReviewer paperReviewer : list) {
            User user1 = userMapper.selectById(paperReviewer.getUserId());
            if (user1.getRole()==3){
                paperReviewerMapper.delete(lambdaQueryWrapper);
            }
        }


        PaperReviewer paperReviewer = new PaperReviewer();
        String reviewer = changeTeacherVo.getReviewerId();
        List<String> reviewerList = Arrays.asList(reviewer.split(","));
        System.out.println(reviewerList);
        int a = reviewerList.size();
        paperReviewer.setPaperId(changeTeacherVo.getId());
        for (int i = 0; i < a; i++) {
            long l = Long.parseLong(reviewerList.get(i));
            paperReviewer.setUserId(l);
            paperReviewer.setPaperId(changeTeacherVo.getId());
            paperReviewer.setId(null);
            save(paperReviewer);
        }

    }
}




