package com.zoulj.msapp.infrastructure.exception;

public enum EmBusinessError implements CommonError {
    //通用错误类型100001
    PARAMETER_VALIDATION_ERROR(10001, "参数不合法"),
    //未知错误
    UNKNOW_ERROR(10002, "未知错误"),
    //20000开头为用户信息相关错误定义
    USER_NOT_EXIST(20001, "用户不存在"),
    USER_LOGIN_FAIL(20002, "用户手机号或密码不正确"),
    USER_NOT_LOGIN(20003, "用户未登录"),
    USER_OTP_FAIL(20004, "验证码不对"),
    //30000开头为交易信息错误定义
    STOCK_NOT_ENOUGH(30001, "库存不足"),
    ORDER_ERROR(30002, "生成订单错误,请查看日志"),
    SQL_ERROR(40001, "SQL错误")
    ;
    private int errCode;
    private String errMsg;

    private EmBusinessError(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMs) {
        this.errMsg = errMsg;
        return this;
    }
}
