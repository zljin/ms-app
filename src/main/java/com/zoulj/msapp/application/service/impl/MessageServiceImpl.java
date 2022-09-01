package com.zoulj.msapp.application.service.impl;

import com.zoulj.msapp.application.service.MessageService;
import com.zoulj.msapp.interfaces.command.MessageCallbackCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class MessageServiceImpl implements MessageService {

    @Override
    public void stateCallback(MessageCallbackCommand messageCallbackCommand) {

        try {
            log.info("stateCallback invoke running..............");
            Thread.sleep(2000);
            log.info("stateCallback invoke end.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
