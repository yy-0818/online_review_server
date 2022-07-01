package com.scientific.audit.common.model.base;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 统一封装API响应结果封装
 */
@ApiModel("API响应结果统一封装")
public class Result{
    private static final long serialVersionUID = 1L;
    // 响应状态
    @ApiModelProperty("响应状态")
    protected Integer status = 0;

    // 响应消息

    public static Result buildOK() {
        return new Result(ResultCode.OK);
    }

    public static Result buildOK(Object data) {
        return Result.build(ResultCode.OK, data);
    }

    public static Result build(ResultCode rsc) {
        return new Result(rsc.getStatus(), rsc.getMsg(), null);
    }

    public static Result build(ResultCode rsc, Object data) {
        return new Result(rsc.getStatus(), rsc.getMsg(), data);
    }

    public static Result build(Integer status, String msg, Object data) {
        return new Result(status, msg, data);
    }

    public static Result ok(Object data) {
        return new Result(data);
    }

    @ApiModelProperty("响应消息")
    protected String msg;

    // 响应数据
    @ApiModelProperty("响应数据")
    protected Object data;


    public static Result ok() {
        return new Result(null);
    }

    public Result() {

    }

    public static Result build(Integer status, String msg) {
        return new Result(status, msg, null);
    }

    public Result(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public Result(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public Boolean isOK() {
        if (null == this.status) {
            return false;
        }
        return this.status == 200;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result [status=" + status + ", msg=" + msg + ", data=" + data + "]";
    }
}