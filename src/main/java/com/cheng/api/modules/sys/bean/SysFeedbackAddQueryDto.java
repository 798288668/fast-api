/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.modules.sys.bean;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 反馈意见表(SysFeedback)实体类
 *
 * @author fengcheng
 * @since 2019-06-17 20:31:11
 */
@Data
public class SysFeedbackAddQueryDto implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 设备
	 */
	private String equipment;
	/**
	 * 系统
	 */
	private String system;
	/**
	 * 客户端版本
	 */
	private String clientVersion;
	/**
	 * 项目版本号
	 */
	private String version;
	/**
	 * 反馈内容
	 */
	private String content;
	/**
	 * 反馈类型(0:联系客服 1:程序bug,2:功能建议,3:内容意见,4:广告问题,5:网络问题,6:其他)
	 */
	private String type;
	/**
	 * 联系方式
	 */
	@NotEmpty
	private String phone;
	/**
	 * 图片url
	 */
	private String url1;
	/**
	 * 图片url
	 */
	private String url2;
	/**
	 * 图片url
	 */
	private String url3;
	/**
	 * 图片url
	 */
	private String url4;

}
