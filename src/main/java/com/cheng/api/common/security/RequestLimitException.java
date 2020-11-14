/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.common.security;


import com.cheng.api.common.base.BaseException;

/**
 * @author fengcheng
 * @version 2017/8/8
 */
public class RequestLimitException extends BaseException {

	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new Request limit exception.
	 */
	RequestLimitException() {
		super();
	}

	/**
	 * Instantiates a new Request limit exception.
	 *
	 * @param message the message
	 */
	RequestLimitException(String message) {
		super(message);
	}

}
