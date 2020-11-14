/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.modules.sys.bean;


import lombok.Data;

import java.io.Serializable;

/**
 * @author fengcheng
 * @version 2017/2/28
 */
@Data
public class SysUserDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	/**
	 * 登录名
	 */
	private String loginName;
	/**
	 * 姓名
	 */
	private String userName;
	/**
	 * 头像
	 */
	private String photo;

	private String secret;
}
