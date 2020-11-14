/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.common.constant;

import static com.cheng.api.common.constant.RedisConst.PREFIX;

/**
 * @author fengcheng
 * @version 2017/4/17
 */
public class RedisLockConst {
	private RedisLockConst() {
	}

	public static final String FIN_ADD_ORDER = PREFIX + "add_order";
	public static final String FIN_REPAIR_ORDER = PREFIX + "repair_order";
	public static final String FIN_CONFIRM_MONEY = PREFIX + "confirm_money";

}
