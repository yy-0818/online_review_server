package com.scientific.audit.common.aspect;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * author: Lunatic
 * since: 2021/10/29 15:10
 */
@Component
@Aspect
@Slf4j
public class WebLogAOP{

    /**
     * 定义切点，切点为ren.lunatic.detect.controller包和子包里任意方法的执行
     */
    @Pointcut("execution(* com.scientific.audit.controller..*(..))")
    public void webLog() {

    }

    /**
     * 前置通知，在切点之前执行的通知
     *
     * @param joinPoint 切点
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            //类名
            String className = joinPoint.getTarget().getClass().getName();
            //请求方法
            String method = joinPoint.getSignature().getName() + "()";
            //方法参数
            String methodParam = Arrays.toString(joinPoint.getArgs());
            //方法描述
            StringBuilder sb = new StringBuilder(1000);
            sb.append("\n");
            sb.append("*********************************Request请求***************************************");
            sb.append("\n");
            sb.append("ClassName     :  ").append(className).append("\n");
            sb.append("RequestMethod :  ").append(method).append("\n");
            sb.append("RequestParams :  ").append(methodParam).append("\n");
            sb.append("RequestType   :  ").append(request.getMethod()).append("\n");
            sb.append("serverAddress :  ").append(request.getScheme()).append("://").append(request.getServerName()).append(":").append(request.getServerPort()).append("\n");
            sb.append("RemoteAddress :  ").append(request.getRemoteAddr()).append("\n");
            sb.append("Token         :  ").append(request.getHeader("token")).append("\n");
            UserAgent userAgent = UserAgentUtil.parse(request.getHeader("User-Agent"));
            sb.append("DeviceName    :  ").append(userAgent.getOs().getName()).append("\n");
            sb.append("BrowserName   :  ").append(userAgent.getBrowser().getName()).append("\n");
            sb.append("UserAgent     :  ").append(request.getHeader("User-Agent")).append("\n");
            sb.append("RequestUri    :  ").append(request.getRequestURI()).append("\n");
            log.debug(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
