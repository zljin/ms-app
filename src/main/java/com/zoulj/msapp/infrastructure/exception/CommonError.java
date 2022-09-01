package com.zoulj.msapp.infrastructure.exception;

public interface CommonError {
    int getErrCode();
    String getErrMsg();
    CommonError setErrMsg(String errMs);
}
