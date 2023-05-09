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
	private String id;
	/**
	 * 
	 */
	private String name;

	/**
	 *
	 */
	private String gender;
	/**
	 *
	 */
	private String age;
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

	private String email;

}
