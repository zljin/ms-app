package com.zoulj.msapp.interfaces.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "消息状态回调参数", description = "消息状态回调参数")
public class MessageCallbackCommand {

    @ApiModelProperty(value = "回调时间", name = "callbackTime", dataType = "String")
    private String callbackTime;

    @ApiModelProperty(value = "回调状态", name = "receiverState", dataType = "String")
    private List<ReceiverStateDTO> receiverState;

    @Data
    @NoArgsConstructor
    private static class ReceiverStateDTO {
        private String language;
        private String messageRequestId;
        private String state;
        private String message;
        private String receiveValue;
    }

}
