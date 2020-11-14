/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.modules.sys.bean;

import com.cheng.api.common.base.BaseQueryDto;
import com.cheng.api.common.constant.SysEnum;
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
public class SysFeedbackListQueryDto extends BaseQueryDto {
	private static final long serialVersionUID = 1L;
	/**
	 * 状态（1:已处理,0:待处理)
	 */
	private SysEnum.FeedbackStatus status;
}
