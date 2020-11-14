/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.common.base;

/**
 * 自定义异常基类
 *
 * @author fengcheng
 * @version 2016/1/21 19:29
 */
public class UnauthorizedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UnauthorizedException() {
	}

	public UnauthorizedException(Throwable cause) {
		super(cause);
	}

	public UnauthorizedException(String message) {
		super(message);
	}

	public UnauthorizedException(String message, Throwable cause) {
		super(message, cause);
	}
}
