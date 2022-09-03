package com.zoulj.msapp.application.service;

import com.zoulj.msapp.infrastructure.exception.BusinessException;
import com.zoulj.msapp.interfaces.command.RegisterCommand;
import com.zoulj.msapp.interfaces.vo.UserVo;

public interface UserService {
    UserVo getUserById(Long id) throws BusinessException;
    void register(RegisterCommand registerCommand) throws BusinessException;
    UserVo login(String email, String password) throws BusinessException;
    void getOtp(String email);
}
