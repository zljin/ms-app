package com.zoulj.msapp.infrastructure.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 项目启动的时候做一个生命周期只有一次的初始化操作
 * 比如你可以把国际化的数据封装到一个map中
 * SpringBoot中CommandLineRunner的作用，也就是项目启动之后就立即执行的操作
 * https://cloud.tencent.com/developer/article/1750000
 *
 */
@Component
@Slf4j
public class StarUpDevTask implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        log.info("初始化工作todo...");
    }
}
