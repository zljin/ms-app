package com.zoulj.msapp;


import com.zoulj.msapp.infrastructure.utils.Convert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;

import javax.annotation.Resource;

@SpringBootTest
class AppTest {

    @Resource
    MessageSource messageSource;

    @Test
    void testGlobalization(){
        String message = messageSource.getMessage("message", null, Convert.getResultLocale("en-US"));
        System.out.println(message);
    }




}
