package com.zoulj.msapp.infrastructure.schedule;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ScheduledJob {

    //@Scheduled(cron = "0/10 * * * * ?")
    public void cronJob1(){
        log.info(DateUtil.now() +" alive ");
    }

}
