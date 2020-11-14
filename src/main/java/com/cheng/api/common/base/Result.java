/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.common.base;


import lombok.Data;

import java.io.Serializable;

/**
 * 响应结果
 *
 * @author fengcheng
 * @version 2016/12/26
 */
@Data
public class Result<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	private String code;
	private String message;
	private T data;

	private Result(String code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	/**
	 * 处理成功
	 *
	 * @return 构造器对象
	 */
	public static <T> Result<T> success() {
		return success(null);
	}

	/**
	 * 静态构造器
	 *
	 * @param data 返回结果
	 * @return 构造器对象
	 */
	public static <T> Result<T> success(T data) {
		return new Result<>(ResultCode.SUCCESS_CODE, ResultCode.SUCCESS, data);
	}

	/**
	 * 静态构造器
	 *
	 * @return 构造器对象
	 */
	public static <T> Result<T> fail() {
		return new Result<>(ResultCode.FAILD_CODE, ResultCode.ERROR_UNKNOWN, null);
	}

	/**
	 * 静态构造器
	 *
	 * @param message 响应码
	 * @return 构造器对象
	 */
	public static <T> Result<T> fail(String message) {
		return new Result<>(ResultCode.FAILD_CODE, message, null);
	}

}
