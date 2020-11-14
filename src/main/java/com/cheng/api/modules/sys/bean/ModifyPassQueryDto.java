/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.modules.sys.bean;

import com.cheng.api.common.constant.RegexConst;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 *
 * @author fengcheng
 * @version 2019-06-17
 */
@Data
public class ModifyPassQueryDto implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 登录名
	 */
	@NotEmpty
	@Pattern(regexp = RegexConst.NAME, message = "登录名格式不正确")
	private String loginName;
	/**
	 * 密码
	 */
	@NotEmpty
	@Pattern(regexp = RegexConst.LOGIN_PASS, message = "密码格式不正确")
	private String password;

	@NotEmpty
	private String oldPassword;
}
