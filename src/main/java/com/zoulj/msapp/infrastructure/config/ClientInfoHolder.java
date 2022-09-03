package com.zoulj.msapp.infrastructure.config;

import com.zoulj.msapp.domain.model.user.UserInfoEntity;
import com.zoulj.msapp.interfaces.vo.UserVo;
import lombok.extern.slf4j.Slf4j;

/**
 * @author leonard
 * @Description
 * @date 2021-11-19 17:03
 */
@Slf4j
public class ClientInfoHolder {

    private static final ThreadLocal<UserInfoEntity> clientInfoHolder = new ThreadLocal<>();

    public ClientInfoHolder() {
    }

    public static void setClientInfo(UserInfoEntity user) {
        clientInfoHolder.set(user);
    }

    public static UserInfoEntity getClientInfo() {
        return clientInfoHolder.get();
    }

    public static void clear() {
        clientInfoHolder.remove();
    }

}
