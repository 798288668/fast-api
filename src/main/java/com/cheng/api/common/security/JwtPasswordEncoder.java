/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.common.security;

import com.cheng.api.common.util.Encodes;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 自定义加密方式
 *
 * @author fengcheng
 * @version 2017/8/8
 */
public class JwtPasswordEncoder implements PasswordEncoder {
	@Override
	public String encode(CharSequence rawPassword) {
		return Encodes.encryptPassword(rawPassword.toString());
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return Encodes.validatePassword(rawPassword.toString(), encodedPassword);
	}
}
