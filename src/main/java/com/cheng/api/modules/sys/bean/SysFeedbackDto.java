/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.modules.sys.bean;

import com.cheng.api.common.base.BaseDto;
import com.cheng.api.common.constant.SysEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 反馈意见表(SysFeedback)实体类
 *
 * @author fengcheng
 * @since 2019-06-17 20:31:11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysFeedbackDto extends BaseDto {
	private static final long serialVersionUID = 1L;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 用户登录名
	 */
	private String loginName;
	/**
	 * 用户姓名
	 */
	private String userName;
	/**
	 * 联系方式
	 */
	private String phone;
	/**
	 * 状态（1:已处理,0:待处理)
	 */
	private SysEnum.FeedbackStatus status;
	/**
	 * 创建日期
	 */
	private Date createDate;
	/**
	 * 处理人id
	 */
	private String handlerId;
	/**
	 * 处理时间
	 */
	private Date handlerTime;
	/**
	 * 处理意见
	 */
	private String handlerInfo;

}
