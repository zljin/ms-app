package com.zoulj.msapp.infrastructure.utils;

/**
 * @author leonard
 * @date 2022/9/3
 * @Description 业务常量
 */
public class AppConstant {
    public static final Integer PROMOTE_ZERO = 0;
    public static final Integer PROMOTE_WAIT = 1;
    public static final Integer PROMOTE_PROCESS = 2;
    public static final Integer PROMOTE_END = 3;


    //redis Key
    public static final String LOGIN_USER_KEY = "login:token:";

    public static final Long LOGIN_USER_TTL = 10L;
}
