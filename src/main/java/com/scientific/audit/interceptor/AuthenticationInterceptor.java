package com.scientific.audit.interceptor;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.scientific.audit.common.model.base.ResultCode;
import com.scientific.audit.common.model.entity.User;
import com.scientific.audit.exception.CustomException;
import com.scientific.audit.service.UserService;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationInterceptor implements HandlerInterceptor{
    @Resource
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token

        //检查有没有需要用户权限的注解

        // 执行认证
        if (StrUtil.isEmpty(token)) {
            throw new CustomException(ResultCode.LOGIN_ERROR);
        }
        // 获取 token 中的 user id
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException e) {
            throw new CustomException(ResultCode.LOGIN_ERROR);
        }
        User user = userService.getById(Integer.parseInt(userId));

        // 验证 token
        try {
            if (ObjectUtil.isNotNull(user)) {
                JWTVerifier jwtVerifierSysUser = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
                jwtVerifierSysUser.verify(token);
            } else {
                throw new CustomException(ResultCode.LOGIN_ERROR);
            }
        } catch (JWTVerificationException e) {
            throw new CustomException(ResultCode.LOGIN_ERROR);
        }


        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
