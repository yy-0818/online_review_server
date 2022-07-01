package com.scientific.audit.service.impl;

import cn.hutool.core.util.StrUtil;
import com.scientific.audit.common.model.entity.Paper;
import com.scientific.audit.common.model.vo.PaperOperationVo;
import com.scientific.audit.service.MailService;
import freemarker.template.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class MailServiceImpl implements MailService{

    @Resource
    Configuration configuration;

    @Resource
    private JavaMailSender mailSender;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    @Async("taskExecutor")
    public void sendMimeMail(String email) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject("验证码邮件");//主题
            //生成随机数
            String code = genRandomCode();
            redisTemplate.boundValueOps(email + "code").set(code, 5, TimeUnit.MINUTES);
            System.out.println(redisTemplate.boundValueOps(email + "code").get());
            mailMessage.setText("您收到的验证码是：" + code + "，用于账号注册，5分钟内有效，请勿泄露，谨防被骗。");//内容
            mailMessage.setTo(email);//发给谁
            mailMessage.setFrom(from);//你自己的邮箱
            mailSender.send(mailMessage);//发送
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 随机生成6位数的验证码
     *
     * @return String code
     */
    public String genRandomCode() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }


    @Override
    @Async("taskExecutor")
    public void failBackFirst(String email, PaperOperationVo paperOperationVo, Paper paper,String title) {
        long startTime = System.currentTimeMillis();
        try {
            log.debug("将发送邮件: {}", email);
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(email);
            helper.setSubject("论文退回邮件");
            Map<String, Object> map = new HashMap<>();
            map.put("title", title);
            if (StrUtil.isNotBlank(paperOperationVo.getContent())) {
                map.put("content", paperOperationVo.getContent());
            }
            if (StrUtil.isNotBlank(paperOperationVo.getOpinion())) {
                map.put("opinion", paperOperationVo.getOpinion());
            }
            if (StrUtil.isNotBlank(paperOperationVo.getReason())) {
                map.put("reason", paperOperationVo.getReason());
            }
            map.put("paperTitle", paper.getTitle());
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate("failBack.ftl"), map);
            helper.setText(text, true);
            mailSender.send(mimeMessage);//发送
            long endTime = System.currentTimeMillis();
            log.debug("邮件发送成功, 运行时间{}秒", (endTime - startTime) / 1000);
        } catch (Exception e) {
            log.error("邮件发送失败: {}", e.getMessage());
        }
    }

    @Override
    @Async("taskExecutor")
    public void toBeModified(String email, PaperOperationVo paperOperationVo, Paper paper) {
        long startTime = System.currentTimeMillis();
        try {
            log.debug("将发送邮件: {}", email);
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(email);
            helper.setSubject("文章待修改邮件");
            Map<String, Object> map = new HashMap<>();
            map.put("title", "修改通知");
            if (StrUtil.isNotBlank(paperOperationVo.getContent())) {
                map.put("content", paperOperationVo.getContent());
            }
            if (StrUtil.isNotBlank(paperOperationVo.getOpinion())) {
                map.put("opinion", paperOperationVo.getOpinion());
            }
            if (StrUtil.isNotBlank(paperOperationVo.getReason())) {
                map.put("reason", paperOperationVo.getReason());
            }
            map.put("paperTitle", paper.getTitle());
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate("toBeModified.ftl"), map);
            helper.setText(text, true);
            mailSender.send(mimeMessage);//发送
            long endTime = System.currentTimeMillis();
            log.debug("邮件发送成功, 运行时间{}秒", (endTime - startTime) / 1000);
        } catch (Exception e) {
            log.error("邮件发送失败: {}", e.getMessage());
        }
    }

    @Override
    @Async("taskExecutor")
    public void passFirst(String email, PaperOperationVo paperOperationVo, Paper paper) {
        long startTime = System.currentTimeMillis();
        try {
            log.debug("将发送邮件: {}", email);
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(email);
            helper.setSubject("初审通过邮件");
            Map<String, Object> map = new HashMap<>();
            map.put("title", "初审通过");
            if (StrUtil.isNotBlank(paperOperationVo.getContent())) {
                map.put("content", paperOperationVo.getContent());
            }
            if (StrUtil.isNotBlank(paperOperationVo.getOpinion())) {
                map.put("opinion", paperOperationVo.getOpinion());
            }
            if (StrUtil.isNotBlank(paperOperationVo.getReason())) {
                map.put("reason", paperOperationVo.getReason());
            }
            map.put("paperTitle", paper.getTitle());
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate("passFirst.ftl"), map);
            helper.setText(text, true);
            mailSender.send(mimeMessage);//发送
            long endTime = System.currentTimeMillis();
            log.debug("邮件发送成功, 运行时间{}秒", (endTime - startTime) / 1000);
        } catch (Exception e) {
            log.error("邮件发送失败: {}", e.getMessage());
        }
    }

    @Override
    @Async("taskExecutor")
    public void passToTeacher(List<String> email) {
        long startTime = System.currentTimeMillis();
        try {
            log.debug("将发送邮件: {}", email);
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            String[] array = email.toArray(new String[email.size()]);
            helper.setTo(array);
            helper.setSubject("新的待审核文章邮件");
            Map<String, Object> map = new HashMap<>();
            map.put("title", "审核文章通知");
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate("passFirstTeacher.ftl"), map);
            helper.setText(text, true);
            mailSender.send(mimeMessage);//发送
            long endTime = System.currentTimeMillis();
            log.debug("邮件发送成功, 运行时间{}秒", (endTime - startTime) / 1000);
        } catch (Exception e) {
            log.error("邮件发送失败: {}", e.getMessage());
        }
    }

    @Override
    @Async("taskExecutor")
    public void passSecond(String email, PaperOperationVo paperOperationVo, Paper paper) {
        long startTime = System.currentTimeMillis();
        try {
            log.debug("将发送邮件: {}", email);
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(email);
            helper.setSubject("文章二审通过邮件");
            Map<String, Object> map = new HashMap<>();
            map.put("title", "二审通过");
            if (StrUtil.isNotBlank(paperOperationVo.getContent())) {
                map.put("content", paperOperationVo.getContent());
            }
            if (StrUtil.isNotBlank(paperOperationVo.getOpinion())) {
                map.put("opinion", paperOperationVo.getOpinion());
            }
            if (StrUtil.isNotBlank(paperOperationVo.getReason())) {
                map.put("reason", paperOperationVo.getReason());
            }
            map.put("paperTitle", paper.getTitle());
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate("passSecond.ftl"), map);
            helper.setText(text, true);
            mailSender.send(mimeMessage);//发送
            long endTime = System.currentTimeMillis();
            log.debug("邮件发送成功, 运行时间{}秒", (endTime - startTime) / 1000);
        } catch (Exception e) {
            log.error("邮件发送失败: {}", e.getMessage());
        }
    }

    @Override
    @Async("taskExecutor")
    public void passThird(String email, PaperOperationVo paperOperationVo, Paper paper) {
        long startTime = System.currentTimeMillis();
        try {
            log.debug("将发送邮件: {}", email);
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(email);
            helper.setSubject("文章终审通过邮件");
            Map<String, Object> map = new HashMap<>();
            map.put("title", "终审通过");
            if (StrUtil.isNotBlank(paperOperationVo.getContent())) {
                map.put("content", paperOperationVo.getContent());
            }
            if (StrUtil.isNotBlank(paperOperationVo.getOpinion())) {
                map.put("opinion", paperOperationVo.getOpinion());
            }
            if (StrUtil.isNotBlank(paperOperationVo.getReason())) {
                map.put("reason", paperOperationVo.getReason());
            }
            map.put("paperTitle", paper.getTitle());
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate("failBack.ftl"), map);
            helper.setText(text, true);
            mailSender.send(mimeMessage);//发送
            long endTime = System.currentTimeMillis();
            log.debug("邮件发送成功, 运行时间{}秒", (endTime - startTime) / 1000);
        } catch (Exception e) {
            log.error("邮件发送失败: {}", e.getMessage());
        }
    }

    @Override
    @Async("taskExecutor")
    public void scheduledTaskStudent(String email, PaperOperationVo paperOperationVo, Paper paper) {
        long startTime = System.currentTimeMillis();
        try {
            log.debug("将发送邮件: {}", email);
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(email);
            helper.setSubject("文章待修改邮件");
            Map<String, Object> map = new HashMap<>();
            map.put("title", "修改通知");
            if (StrUtil.isNotBlank(paperOperationVo.getContent())) {
                map.put("content", paperOperationVo.getContent());
            }
            if (StrUtil.isNotBlank(paperOperationVo.getOpinion())) {
                map.put("opinion", paperOperationVo.getOpinion());
            }
            if (StrUtil.isNotBlank(paperOperationVo.getReason())) {
                map.put("reason", paperOperationVo.getReason());
            }
            map.put("paperTitle", paper.getTitle());
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate("student.ftl"), map);
            helper.setText(text, true);
            mailSender.send(mimeMessage);//发送
            long endTime = System.currentTimeMillis();
            log.debug("邮件发送成功, 运行时间{}秒", (endTime - startTime) / 1000);
        } catch (Exception e) {
            log.error("邮件发送失败: {}", e.getMessage());
        }
    }

    @Override
    @Async("taskExecutor")
    public void scheduledTaskTeacher(String email, PaperOperationVo paperOperationVo, Paper paper) {
        long startTime = System.currentTimeMillis();
        try {
            log.debug("将发送邮件: {}", email);
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(email);
            helper.setSubject("文章审核提醒");
            Map<String, Object> map = new HashMap<>();
            map.put("title", "审核提醒通知");
            if (StrUtil.isNotBlank(paperOperationVo.getContent())) {
                map.put("content", paperOperationVo.getContent());
            }
            if (StrUtil.isNotBlank(paperOperationVo.getOpinion())) {
                map.put("opinion", paperOperationVo.getOpinion());
            }
            if (StrUtil.isNotBlank(paperOperationVo.getReason())) {
                map.put("reason", paperOperationVo.getReason());
            }
            map.put("paperTitle", paper.getTitle());
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate("teacher.ftl"), map);
            helper.setText(text, true);
            mailSender.send(mimeMessage);//发送
            long endTime = System.currentTimeMillis();
            log.debug("邮件发送成功, 运行时间{}秒", (endTime - startTime) / 1000);
        } catch (Exception e) {
            log.error("邮件发送失败: {}", e.getMessage());
        }
    }
}



