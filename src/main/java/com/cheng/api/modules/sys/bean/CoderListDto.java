/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.modules.sys.bean;


import com.cheng.api.common.constant.SysEnum;
import lombok.Data;

/**
 * @author fengcheng
 * @version 2017/2/28
 */
@Data
public class CoderListDto {

	private static final long serialVersionUID = 1L;

	private String id;
	/**
	 * 上级Id
	 */
	private String parentId;
	/**
	 * 登录名
	 */
	private String loginName;
	/**
	 * 姓名
	 */
	private String userName;
	/**
	 * 平台
	 */
	private SysEnum.Platform platform;
}
