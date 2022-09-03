package com.zoulj.msapp.interfaces.command;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author leonard
 * @date 2022/9/3
 * @Description TODO
 */
@Data
@ToString
public class RegisterCommand {
    @NotBlank(message = "验证码不能为空")
    private String otpCode;
    @NotBlank(message = "用户名不能为空")
    private String name;

    @NotNull(message = "性别不能为空")
    private Integer gender;

    @NotNull(message = "年龄不能不填写")
    @Min(value = 0, message = "年龄必须大于0岁")
    @Max(value = 150, message = "年龄必须小于150岁")
    private Integer age;

    @NotBlank(message = "手机号不能为空")
    private String telphone;

    private String registerMode;
    private String thirdPartyId;

    @NotBlank(message = "密码不能为空")
    private String password;
    @NotBlank(message = "邮件不能为空")
    private String email;
}
