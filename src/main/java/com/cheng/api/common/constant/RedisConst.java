package com.cheng.api.common.constant;

/**
 * @author fengcheng
 */
public class RedisConst {

	private RedisConst() {
	}

	public static final String PREFIX = "admin.";

	public static final String USER_LOGIN_NAME = PREFIX + "userLoginName.";
	public static final String USER_LOGIN_TOKEN = PREFIX + "userLoginToken.";

	/**
	 * 字典列表
	 */
	public static final String DIC_LIST = PREFIX + "dicList";
}
