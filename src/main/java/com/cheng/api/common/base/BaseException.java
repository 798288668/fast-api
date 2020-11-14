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
public class BaseException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BaseException() {
	}

	public BaseException(Throwable cause) {
		super(cause);
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}
}
