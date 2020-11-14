package com.cheng.api.common.constant;

import com.cheng.api.common.base.IPropertyEnum;

/**
 *
 * @author fengcheng
 * @version 2019-07-02
 */
public class SysEnum {

	public enum FeedbackStatus implements IPropertyEnum {
		/**
		 * 待处理
		 */
		WAIT_DISPOSE("0"),
		/**
		 * 已处理
		 */
		DISPOSE("1");

		private String value;

		@Override
		public String getValue() {
			return value;
		}

		FeedbackStatus(String value) {
			this.value = value;
		}
	}

	public enum FeedbackType implements IPropertyEnum {
		/**
		 * 联系客服
		 */
		CONTACT_US("0"),
		/**
		 * 程序bug
		 */
		BUG("1"),
		/**
		 * 功能建议
		 */
		FUNCTION_ADVICE("2"),
		/**
		 * 内容意见
		 */
		CONTENT_ADVICE("3"),
		/**
		 * 广告问题
		 */
		ADV_PROBLEM("4"),
		/**
		 * 网络问题
		 */
		NETWORK_PROBLEM("5"),
		/**
		 * 其他
		 */
		OTHER("6"),
		;

		private String value;

		@Override
		public String getValue() {
			return value;
		}

		FeedbackType(String value) {
			this.value = value;
		}
	}

	public enum IpGetMode implements IPropertyEnum {
		/**
		 * 对方提供
		 */
		SEND("0"),
		/**
		 * 自己获取
		 */
		GET("1");

		private String value;

		@Override
		public String getValue() {
			return value;
		}

		IpGetMode(String value) {
			this.value = value;
		}
	}

	public enum IsSys implements IPropertyEnum {
		/**
		 * 否
		 */
		NO("0"),
		/**
		 * 是
		 */
		YES("1");

		private String value;

		@Override
		public String getValue() {
			return value;
		}

		IsSys(String value) {
			this.value = value;
		}
	}

	public enum MenuType implements IPropertyEnum {
		/**
		 * 菜单
		 */
		MENU("0"),
		/**
		 * 按钮
		 */
		BUTTON("1");

		private String value;

		@Override
		public String getValue() {
			return value;
		}

		MenuType(String value) {
			this.value = value;
		}
	}

	public enum UserType implements IPropertyEnum {
		/**
		 * 管理平台
		 */
		ADMIN("0"),
		/**
		 * APP
		 */
		APP("1");

		private String value;

		@Override
		public String getValue() {
			return value;
		}

		UserType(String value) {
			this.value = value;
		}
	}

	public enum UserStatus implements IPropertyEnum {
		/**
		 * 正常
		 */
		NORMAL("0"),
		/**
		 * 冻结
		 */
		FREEZE("1"),
		/**
		 * 审核中
		 */
		AUDIT("2"),
		/**
		 * 已驳回
		 */
		REJECT("3");

		private String value;

		@Override
		public String getValue() {
			return value;
		}

		UserStatus(String value) {
			this.value = value;
		}
	}

	public enum Platform implements IPropertyEnum {
		/**
		 * 系统
		 */
		SYSTEM("0"),
		/**
		 * 一级代理
		 */
		FIRST_AGENT("4"),
		/**
		 * 二级代理
		 */
		SECOND_AGENT("5");

		private String value;

		@Override
		public String getValue() {
			return value;
		}

		Platform(String value) {
			this.value = value;
		}
	}
}
