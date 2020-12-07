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
public class SysUserAddQueryDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	/**
	 * 登录名
	 */
	@NotEmpty
	@Pattern(regexp = RegexConst.LOGIN_NAME, message = "登录名格式不正确")
	private String loginName;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 姓名
	 */
	@NotEmpty
	@Pattern(regexp = RegexConst.USER_NAME, message = "姓名格式不正确")
	private String userName;
	/**
	 * 角色Id
	 */
	@NotEmpty
	private String roleId;
	/**
	 * 联系电话
	 */
	private String phone;
	/**
	 * 用户类型
	 */
	@NotNull
	private SysEnum.UserType userType;
	/**
	 * 平台
	 */
	private SysEnum.Platform platform;
	/**
	 * ip获取方式
	 */
	private SysEnum.IpGetMode ipGetMode;
	/**
	 * 通道编号
	 */
	private String no;
	/**
	 * 通道密钥
	 */
	private String secret;
	/**
	 * API地址
	 */
	private String url;
}
