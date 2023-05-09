package com.zoulj.msapp.interfaces.controller;

import com.zoulj.msapp.application.service.UserService;
import com.zoulj.msapp.infrastructure.annotation.TokenCheck;
import com.zoulj.msapp.infrastructure.exception.BusinessException;
import com.zoulj.msapp.infrastructure.utils.AESUtil;
import com.zoulj.msapp.interfaces.command.RegisterCommand;
import com.zoulj.msapp.interfaces.vo.CommonReturnType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController{

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @TokenCheck(check = false)
    public CommonReturnType register(@Valid @RequestBody RegisterCommand registerCommand) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        String password = registerCommand.getPassword();
        registerCommand.setPassword(AESUtil.encrypt(password));
        userService.register(registerCommand);
        return CommonReturnType.create(null);
    }

    @PostMapping("/login")
    @TokenCheck(check = false)
    public CommonReturnType login(@RequestParam(name = "email") String email,
                                  @RequestParam(name = "password") String password) throws BusinessException {

        return CommonReturnType.create(userService.login(email, password));
    }

    @GetMapping("/get-otp")
    @TokenCheck(check = false)
    public CommonReturnType getOtp(@RequestParam(name = "email") String email) {
        userService.getOtp(email);
        return CommonReturnType.create(null);
    }

    @GetMapping("/get")
    public CommonReturnType getUser(@RequestParam(name = "id") String id) throws BusinessException {
        return CommonReturnType.create(userService.getUserById(id));
    }
}
