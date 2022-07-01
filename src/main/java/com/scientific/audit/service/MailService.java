package com.scientific.audit.service;


import com.scientific.audit.common.model.entity.Paper;
import com.scientific.audit.common.model.vo.PaperOperationVo;

import java.util.List;

public interface MailService{

    //注册账号的邮箱验证码发送
    void sendMimeMail(String email);

    //论文退回
    void failBackFirst(String email, PaperOperationVo paperOperationVo, Paper paper,String title);

    //论文待修改
    void toBeModified(String email, PaperOperationVo paperOperationVo, Paper paper);

    //初审通过(学生)
    void passFirst(String email, PaperOperationVo paperOperationVo, Paper paper);

    //各种通过(老师)
    void passToTeacher(List<String> email);

    //二审通过(学生)
    void passSecond(String email, PaperOperationVo paperOperationVo, Paper paper);

    //终审通过(学生)
    void passThird(String email, PaperOperationVo paperOperationVo, Paper paper);

    //催学生修改
    void scheduledTaskStudent(String email, PaperOperationVo paperOperationVo, Paper paper);

    //催老师审核
    void scheduledTaskTeacher(String email, PaperOperationVo paperOperationVo, Paper paper);

}
