package com.cheng.api.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author fengcheng
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SysConst {

	public static final String HTTP = "http";
	public static final String HTTPS = "https";
	public static final String DOT = ".";

	public static final String MAX_MONEY_STR = "999999999";
	public static final String MIN_MONEY_STR = "0.01";
	public static final BigDecimal MAX_MONEY = new BigDecimal(MAX_MONEY_STR);
	public static final BigDecimal MIN_MONEY = new BigDecimal(MIN_MONEY_STR);
	public static final BigDecimal PERCENT = new BigDecimal("0.01");
	public static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

	public static final String USER_ADMIN_ID = "1";
	public static final String USER_SUP_ID = "0";
	public static final String ROLE_FIRST_AGENT = "id_firstAgent";
	public static final String ROLE_SECOND_AGENT = "id_secondAgent";
}
