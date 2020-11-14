/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.modules.sys.bean;

import com.cheng.api.common.constant.SysEnum;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *
 * @author fengcheng
 * @version 2019-06-17
 */
@Data
public class LoginQueryDto implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 登录名
	 */
	@NotEmpty
	@Length(min = 2, max = 16, message = "登录名长度必须介于 2 和 20 之间")
	private String loginName;
	/**
	 * 	密码
	 */
	@NotEmpty
	@Length(min = 4, max = 16, message = "密码长度必须介于 4 和 16 之间")
	private String password;

	/**
	 * 来源
	 */
	@NotNull
	private SysEnum.UserType userType;
}
