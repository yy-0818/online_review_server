package com.scientific.audit.common.Timing;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.scientific.audit.common.model.entity.Paper;
import com.scientific.audit.common.model.entity.PaperReviewer;
import com.scientific.audit.common.model.entity.User;
import com.scientific.audit.common.model.vo.PaperOperationVo;
import com.scientific.audit.mapper.PaperMapper;
import com.scientific.audit.mapper.PaperReviewerMapper;
import com.scientific.audit.mapper.UserMapper;
import com.scientific.audit.service.MailService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Component
public class ScheduledTask{
    @Resource
    PaperMapper paperMapper;
    @Resource
    MailService mailService;
    @Resource
    UserMapper userMapper;
    @Resource
    PaperReviewerMapper paperReviewerMapper;


    @Scheduled(cron = "0 0 10 1/5 * ? ")
    @Async("taskExecutor")
    public void scheduledTaskStudent() {
        System.out.println("任务执行时间：" + LocalDateTime.now());
        PaperOperationVo paperOperationVo = new PaperOperationVo();
        paperOperationVo.setContent("您七天未上传修改后的论文，请及时上传");
        List<Paper> papers = paperMapper.scheduledTaskStudent();
        for (Paper paper : papers) {
            Long uploaderId = paper.getUploaderId();
            User user = userMapper.selectById(uploaderId);
            String email = user.getEmail();
            mailService.scheduledTaskStudent(email, paperOperationVo, paper);
        }
    }

    @Scheduled(cron = "0 0 10 1/5 * ? ")
    @Async("taskExecutor")
    public void scheduledTaskTeacher() {
        System.out.println("任务执行时间：" + LocalDateTime.now());
        PaperOperationVo paperOperationVo = new PaperOperationVo();
        paperOperationVo.setContent("由于及时的评论对作者来说至关重要，我们期待尽快收到您的评论。如果您的审查因任何原因而被推迟，请告诉我们。");
        List<Paper> papers = paperMapper.scheduledTaskTeacher();
        List<Long> paperIds = new ArrayList<>();
        for (Paper paper : papers) {
            Long paperId = paper.getId();
            paperIds.add(paperId);
        }
        for (Long paperId : paperIds) {
            Paper paper = paperMapper.selectById(paperId);
            LambdaQueryWrapper<PaperReviewer> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(PaperReviewer::getPaperId,paperId);
            List<PaperReviewer> list = paperReviewerMapper.selectList(lambdaQueryWrapper);
            for (PaperReviewer paperReviewer : list) {
                Long userId = paperReviewer.getUserId();
                User user = userMapper.selectById(userId);
                String email = user.getEmail();
                mailService.scheduledTaskTeacher(email, paperOperationVo, paper);
            }

        }


    }







}