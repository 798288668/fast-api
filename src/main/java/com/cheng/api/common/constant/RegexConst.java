package com.cheng.api.common.constant;

/**
 *
 * @author fengcheng
 * @version 2019-07-22
 */
public class RegexConst {
	private RegexConst() {
	}

	/**
	 * 手机号：以1开头的11位数
	 */
	public static final String PHONE = "^((1[0-9]{1})+\\d{9})$";

	/**
	 * 登录名：支持字母、数字、汉字、_、-五种符号中一种或多种类型的组合，不区分字母大小写
	 */
	public static final String LOGIN_NAME = "^(?=.*[A-Za-z\\-\\_0-9])[A-Za-z\\-\\_0-9]{2,20}";

	/**
	 * 姓名：支持字母、汉字中一种或多种类型的组合，不区分字母大小写
	 */
	public static final String USER_NAME = "^(?=.*[A-Za-z\\u4e00-\\u9fa5])[A-Za-z\\u4e00-\\u9fa5]{2,20}";

	/**
	 * 登录密码
	 */
	public static final String LOGIN_PASS = "^[\\w_-]{6,16}$";

	/**
	 * 密码由6-16位的字母、数字或下划线组成
	 */
	public static final String PAY_PASS = "^[\\w_-]{6,16}$";

	/**
	 * 邮箱格式
	 */
	public static final String EMAIL = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$";

}
