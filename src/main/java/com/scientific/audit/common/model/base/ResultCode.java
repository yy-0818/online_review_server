package com.scientific.audit.common.model.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public enum ResultCode{
    /**
     * exit OK
     */
    QUIT(200, "退出成功"),

    /**
     * OK
     */
    OK(200, "OK"),

    /**
     * 访问超时
     */
    TIME_OUT(130, "访问超时"),

    /**
     * 参数解析失败
     */
    BAD_REQUEST(400, "参数解析失败"),

    /**
     * 记录不存在
     */
    NOT_EXIST(404, "记录不存在"),

    /**
     * 不支持当前请求方法
     */
    METHOD_NOT_ALLOWED(405, "不支持当前请求方法"),

    /**
     * 服务器运行异常
     */
    SYSTEM_ERR(500, "服务器运行异常"),

    /**
     * 该用户不存在或密码错误
     */
    NOT_EXIST_USER_OR_ERROR_PWD(10000, "该用户不存在或账号密码错误"),

    /**
     * 该用户不存在
     */
    NOT_EXIST_USER(10001,"该用户不存在"),

    /**
     * 该用户不存在或密码错误, 已达到最大尝试次数
     */
    NOT_EXIST_USER_OR_ERROR_PWD_AND_MAX_RETRY(10000, "该用户不存在或账号密码错误, 已达最大重试次数, 账户已锁定"),

    /**
     * 登录异常
     */
    LOGIN_ERROR(10003, "登录异常"),

    /**
     * 您没有该权限
     */
    NO_AUTH_ERROR(10004, "您没有该权限"),

    /**
     * 卡号或卡密不正确
     */
    NOT_ERROR_CARD_NO_OR_ERROR_PWD(10005, "卡号或卡密不正确"),

    /**
     * 请绑定手机号
     */
    BIND_PHONE(10010, "请绑定手机号"),

    /**
     * 上传文件异常
     */
    UPLOAD_ERROR(20000, "上传文件异常"),

    /**
     * 无效的验证码
     */
    INVALID_CAPTCHA(30005, "无效的验证码"),

    /**
     * 该用户已被冻结
     */
    USER_FROZEN(40000, "该用户已被冻结"),

    /**
     * 该账号已存在
     */
    USER_ACCOUNT_EXIST(6002, "该账号已存在");


    @Getter
    @Setter
    private int status;

    @Getter
    @Setter
    private String msg;


    private ResultCode(int code, String msg) {
        this.status = code;
        this.msg = msg;
    }

}