/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.common.base;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author fengcheng
 * @version 2017/2/28
 */
@Data
public abstract class BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 实体编号（唯一标识）
	 */
	@Id
	protected String id;

	/**
	 * 删除标记（0：正常；1：删除；）
	 */
	public static final String DEL_FLAG_NORMAL = "0";
	public static final String DEL_FLAG_DELETE = "1";
}
