/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.common.config;

/**
 * 全局常量
 *
 * @author fengcheng
 * @version 2016-01-15 09:56:22
 */
public class Global {

	private Global() {
	}

	private static final SystemProperties SYSTEM_PROPERTIES = SpringContextHolder.getBean(SystemProperties.class);

	/**
	 * 获取上传文件的根目录
	 */
	public static String getFileUploadPath() {
		return SYSTEM_PROPERTIES.getFileUploadPath();
	}

	/**
	 * 获取文件服务器地址
	 */
	public static String getFileAccessPath() {
		return SYSTEM_PROPERTIES.getFileAccessPath();
	}
}
