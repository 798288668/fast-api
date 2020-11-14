/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.modules.sys.bean;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author fengcheng
 * @version 2017/2/28
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CoderTreeDto extends TreeNodeDto<CoderTreeDto> {

	private static final long serialVersionUID = 1L;
	/**
	 * 登录名
	 */
	private String loginName;
	/**
	 * 姓名
	 */
	private String userName;
}
