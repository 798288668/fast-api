/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.modules.sys.bean;


import com.cheng.api.common.base.DataEntity;
import com.cheng.api.common.constant.SysEnum;
import com.cheng.api.common.util.StringUtils;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * @author fengcheng
 * @version 2017/2/28
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysUser extends DataEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 上级
	 */
	private String parentId;
	/**
	 * 归属公司
	 */
	private String companyId;
	/**
	 * 归属部门
	 */
	private String officeId;
	/**
	 * 登录名
	 */
	private String loginName;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 工号
	 */
	private String no;
	/**
	 * 姓名
	 */
	private String userName;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 头像
	 */
	private String photo;
	/**
	 * 用户类型
	 */
	private SysEnum.UserType userType;
	/**
	 * 用户状态
	 */
	private SysEnum.UserStatus userStatus;
	/**
	 * 平台
	 */
	private SysEnum.Platform platform;
	/**
	 * 最后登陆IP
	 */
	private String loginIp;
	/**
	 * 最后登陆日期
	 */
	private Date loginDate;
	/**
	 * 最后一次密码重置时间
	 */
	private Date lastPasswordResetDate;
	/**
	 * API地址
	 */
	private String url;
	/**
	 * 私钥key
	 */
	private String secret;
	/**
	 * ip获取方式
	 */
	private SysEnum.IpGetMode ipGetMode;

	/**
	 * 拥有角色列表
	 */
	@Transient
	private List<SysRole> sysRoleList = Lists.newArrayList();

	/**
	 * 拥有菜单列表
	 */
	@Transient
	private List<SysMenu> sysMenuList = Lists.newArrayList();

	/**
	 * 获取权限字符串列表
	 */
	public List<String> getPermissions() {
		List<String> permissions = Lists.newArrayList();
		for (SysMenu sysMenu : sysMenuList) {
			if (StringUtils.isNotEmpty(sysMenu.getPermission())) {
				permissions.add(sysMenu.getPermission());
			}
		}
		return permissions;
	}
}
