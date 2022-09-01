package com.zoulj.msapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MsAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsAppApplication.class, args);
    }
}
