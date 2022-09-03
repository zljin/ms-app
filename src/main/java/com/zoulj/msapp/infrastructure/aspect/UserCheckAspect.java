package com.zoulj.msapp.infrastructure.aspect;

import com.zoulj.msapp.domain.model.user.UserInfoEntity;
import com.zoulj.msapp.infrastructure.config.ClientInfoHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Slf4j
@Aspect
@Component
public class UserCheckAspect {

    @Pointcut("@annotation(com.zoulj.msapp.infrastructure.annotation.LoginCheck)")
    public void loginCheck() {
    }

    @Before("loginCheck()")
    public void doBefore(JoinPoint joinPoint) {
        UserInfoEntity clientInfo = ClientInfoHolder.getClientInfo();
        if (clientInfo == null) {
            //throw new BusinessException(EmBusinessError.USER_NOT_LOGIN);
            log.error("EmBusinessError.USER_NOT_LOGIN");
        }
    }
}
