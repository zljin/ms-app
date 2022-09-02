package com.zoulj.msapp.domain.model.user;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


@Data
@TableName("user_info")
public class UserInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Long id;
	/**
	 * 
	 */
	private String name;
	/**
	 * 1为男性，2为女性
	 */
	private Integer gender;
	/**
	 * 
	 */
	private Integer age;
	/**
	 * 
	 */
	private String telphone;
	/**
	 * //byphone,bywechar,byalipay
	 */
	private String registerMode;
	/**
	 * 
	 */
	private String thirdPartyId;

}
