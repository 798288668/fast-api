/*
 * Copyright &copy; cc All rights reserved.
 */
package com.cheng.api.modules.sys.bean;

import com.cheng.api.common.constant.SysEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单Entity
 *
 * @author fengcheng
 * @version 2016/7/28
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysMenuDto extends TreeNodeDto<SysMenuDto> {


	private static final long serialVersionUID = 1L;

	/**
	 * 名称
	 */
	private String name;
	/**
	 * 链接
	 */
	private String permission;
	/**
	 * 0 菜单 1 按钮
	 */
	private SysEnum.MenuType type;

}
