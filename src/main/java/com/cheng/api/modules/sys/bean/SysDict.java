/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.modules.sys.bean;

import com.cheng.api.common.base.DataEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典bean
 *
 * @author fengcheng
 * @version 2016/7/28
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysDict extends DataEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 数据值
	 */
	private String value;
	/**
	 * 标签名
	 */
	private String label;
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 父Id
	 */
	private String parentId;

}
