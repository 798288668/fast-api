/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.common.security;

import com.cheng.api.modules.sys.bean.SysUser;
import com.cheng.api.modules.sys.utils.UserUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author fengcheng
 * @version 2017/8/8
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) {
		SysUser sysUser = UserUtils.getUserByLoginName(username);

		if (sysUser != null) {
			return JwtUserFactory.create(sysUser);
		} else {
			throw new UsernameNotFoundException(String.format("No sysUser found with username '%s'.", username));
		}
	}
}
