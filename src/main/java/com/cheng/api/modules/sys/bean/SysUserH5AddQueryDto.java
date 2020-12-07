/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.modules.sys.bean;


import com.cheng.api.common.constant.RegexConst;
import com.cheng.api.common.constant.SysEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author fengcheng
 * @version 2017/2/28
 */
@Data
public class SysUserH5AddQueryDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 邀请人id
	 */
	@NotEmpty
	private String inviterId;
	/**
	 * 登录名
	 */
	@Pattern(regexp = RegexConst.LOGIN_NAME, message = "登录名格式不正确")
	private String loginName;
	/**
	 * 密码
	 */
	@Pattern(regexp = RegexConst.LOGIN_PASS, message = "密码格式不正确")
	private String password;
	/**
	 * 姓名
	 */
	@Pattern(regexp = RegexConst.USER_NAME, message = "姓名格式不正确")
	private String userName;
	/**
	 * 联系电话
	 */
	@Pattern(regexp = RegexConst.PHONE, message = "手机号码格式不正确")
	private String phone;
	/**
	 * 平台
	 */
	@NotNull
	private SysEnum.Platform platform;
}
