/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.modules.sys.bean;


import com.cheng.api.common.base.BaseDto;
import com.cheng.api.common.constant.SysEnum;
import com.cheng.api.common.constant.SysEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author fengcheng
 * @version 2017/2/28
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysUserListDto extends BaseDto {

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
	 * 联系电话
	 */
	private String phone;
	/**
	 * 角色ID
	 */
	private String roleId;
	/**
	 * 角色
	 */
	private String role;
	/**
	 * 用户状态
	 */
	private SysEnum.UserStatus userStatus;
	/**
	 * 上级Id
	 */
	private String parentId;
	/**
	 * 上级
	 */
	private String parentName;
	/**
	 * 费率
	 */
	private BigDecimal rate;
	/**
	 * 代理商
	 */
	private String agent;
	/**
	 * 创建日期
	 */
	protected Date createDate;
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
