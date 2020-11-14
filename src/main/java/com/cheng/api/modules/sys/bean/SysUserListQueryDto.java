/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.modules.sys.bean;

import com.cheng.api.common.base.BaseQueryDto;
import com.cheng.api.common.constant.SysEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 *
 * @author fengcheng
 * @version 2019-06-17
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysUserListQueryDto extends BaseQueryDto {
	private static final long serialVersionUID = 1L;

	private String userName;

	private SysEnum.Platform platform;

	private SysEnum.UserStatus userStatus;

	private SysEnum.UserType userType;

	private String parentName;

	private List<SysEnum.Platform> platforms;
	/**
	 * 组员ID
	 */
	private List<String> userIds;
}
