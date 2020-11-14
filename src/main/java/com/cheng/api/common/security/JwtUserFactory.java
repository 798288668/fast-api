/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.common.security;

import com.cheng.api.common.constant.SysEnum;
import com.cheng.api.modules.sys.bean.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fengcheng
 * @version 2017/8/8
 */
public final class JwtUserFactory {

	private JwtUserFactory() {
	}

	static JwtUser create(SysUser sysUser) {
		return new JwtUser(sysUser.getId(), sysUser.getLoginName(), sysUser.getPassword(),
				mapToGrantedAuthorities(sysUser.getPermissions()), sysUser.getLastPasswordResetDate(),
				sysUser.getUserStatus() == SysEnum.UserStatus.NORMAL);
	}

	private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
		return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}
}

