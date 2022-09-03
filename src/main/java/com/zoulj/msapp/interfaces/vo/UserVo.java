package com.zoulj.msapp.interfaces.vo;

import lombok.Data;

/**
 * @author leonard
 * @date 2022/9/3
 * @Description TODO
 */
@Data
public class UserVo{
    private Long id;
    private String name;

    private Integer gender;

    private Integer age;

    private String telphone;
    private String registerMode;
    private String thirdPartyId;


    private String email;

    private String token;

}
