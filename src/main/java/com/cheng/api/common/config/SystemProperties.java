/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author fengcheng
 * @version 2017/2/28
 */
@Configuration
@ConfigurationProperties(prefix = "sys")
public class SystemProperties {

	private boolean startCleanCache = true;
	private String fileUploadPath;

	private String fileAccessPath;

	public boolean isStartCleanCache() {
		return this.startCleanCache;
	}

	public void setStartCleanCache(boolean startCleanCache) {
		this.startCleanCache = startCleanCache;
	}

	public String getFileUploadPath() {
		return fileUploadPath;
	}

	public void setFileUploadPath(String fileUploadPath) {
		this.fileUploadPath = fileUploadPath;
	}

	public String getFileAccessPath() {
		return fileAccessPath;
	}

	public void setFileAccessPath(String fileAccessPath) {
		this.fileAccessPath = fileAccessPath;
	}
}
