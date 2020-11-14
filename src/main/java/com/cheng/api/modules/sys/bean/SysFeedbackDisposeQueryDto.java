/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.modules.sys.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 反馈意见表(SysFeedback)实体类
 *
 * @author fengcheng
 * @since 2019-06-17 20:31:11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysFeedbackDisposeQueryDto extends IdQueryDto {
	private static final long serialVersionUID = 1L;
	/**
	 * 处理意见
	 */
	private String handlerInfo;

}
