package com.zoulj.msapp.interfaces.controller;

import com.zoulj.msapp.infrastructure.annotation.LoginCheck;
import com.zoulj.msapp.infrastructure.annotation.TokenCheck;
import com.zoulj.msapp.infrastructure.utils.Convert;
import com.zoulj.msapp.interfaces.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;

@Slf4j
@RestController
public class HealthController {

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/health")
    @TokenCheck(check = false)
    public R health() throws Exception {
        StringBuilder sb = new StringBuilder();
        String message = messageSource.getMessage("message", null, Convert.getResultLocale("en-US"));
        sb.append("globalization "+message);
        sb.append(" ms-app is health.");
        sb.append(" IP: " + InetAddress.getLocalHost().getHostAddress());
        return new R<>(sb.toString());
    }

    @LoginCheck
    @GetMapping("/hello")
    public R<String> hello(){
        return new R<String>("hello,测试切面");
    }
}
