package com.zoulj.msapp.application.service;


import com.zoulj.msapp.interfaces.command.MessageCallbackCommand;

public interface MessageService {

    void stateCallback(MessageCallbackCommand messageCallbackCommand);
}
