/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.modules.sys.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 字典bean
 *
 * @author fengcheng
 * @version 2016/7/28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysDictDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 数据值
	 */
	private String value;
	/**
	 * 标签名
	 */
	private String label;

}
