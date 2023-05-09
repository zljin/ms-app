package com.zoulj.msapp.interfaces.command;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.*;

@Data
@ToString
public class RegisterCommand {
    @NotBlank(message = "验证码不能为空")
    private String otpCode;
    @NotBlank(message = "用户名不能为空")
    private String name;

    @NotNull(message = "性别不能为空")
    @Pattern(regexp = "(^Man$|^Woman$|^UGM$)", message = "sex 值不在可选范围")
    private String gender;

    @Pattern(regexp = "(^(1[0-9]|[1-9][0-9])$)", message = "范围在10到99岁之间")
    private String age;

    @NotBlank(message = "手机号不能为空")
    private String telphone;

    private String registerMode;
    private String thirdPartyId;

    @NotBlank(message = "密码不能为空")
    private String password;
    @NotBlank(message = "邮件不能为空")
    @Email(message = "email 格式不正确")
    private String email;
}
