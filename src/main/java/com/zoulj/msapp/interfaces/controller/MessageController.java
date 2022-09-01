package com.zoulj.msapp.interfaces.controller;


import cn.hutool.json.JSONObject;
import com.zoulj.msapp.application.service.MessageService;
import com.zoulj.msapp.interfaces.command.MessageCallbackCommand;
import com.zoulj.msapp.interfaces.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/message")
@Api(tags = "消息模块", value = "MessageController")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @ApiOperation(value = "消息状态回调", notes = "消息状态回调")
    @PostMapping("/state-callback")
    public R<String> stateCallback(@RequestBody MessageCallbackCommand messageCallbackCommand) {
        messageService.stateCallback(messageCallbackCommand);
        return new R<String>(new JSONObject(messageCallbackCommand).toString());
    }

}
