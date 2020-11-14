/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.modules.sys.bean;


import com.cheng.api.common.config.DecimalSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author fengcheng
 * @version 2017/2/28
 */
@Data
public class UserFundDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 账户总金额
	 */
	private BigDecimal accountMoney;
	/**
	 * 账户可用余额
	 */
	private BigDecimal balance;
	/**
	 * 账户冻结金额
	 */
	private BigDecimal freezeMoney;
	/**
	 * 费率
	 */
	private BigDecimal rate;

	/**
	 * 总收益
	 */
	private BigDecimal income;
	/**
	 * 已提现
	 */
	private BigDecimal outIncome;
	/**
	 * 可提现
	 */
	private BigDecimal balanceIncome;

	/**
	 * 今日收益
	 */

	@JsonSerialize(using = DecimalSerialize.class)
	private BigDecimal todayIncome;
	/**
	 * 昨日收益
	 */

	@JsonSerialize(using = DecimalSerialize.class)
	private BigDecimal yesterdayIncome;
	/**
	 * 本月收益
	 */

	@JsonSerialize(using = DecimalSerialize.class)
	private BigDecimal monthIncome;

	/**
	 * 总订单数
	 */
	private Long orderCount;
	/**
	 * 今日订单数
	 */
	private Long todayOrderCount;
	/**
	 * 昨日订单数
	 */
	private Long yesterdayOrderCount;
	/**
	 * 本月订单数
	 */
	private Long monthOrderCount;

	/**
	 * 总订单金额
	 */
	private BigDecimal orderMoney;
	/**
	 * 今日订单金额
	 */
	private BigDecimal todayOrderMoney;
	/**
	 * 昨日订单金额
	 */
	private BigDecimal yesterdayOrderMoney;
	/**
	 * 本月订单金额
	 */
	private BigDecimal monthOrderMoney;

	/**
	 * 今日提现次数
	 */
	private Long dayOutTimes;
	/**
	 * 今日充值次数
	 */
	private Long dayInTimes;
	/**
	 * 今日提现金额（元）
	 */
	private BigDecimal dayOutMoney;
	/**
	 * 今日充值金额（元）
	 */
	private BigDecimal dayInMoney;
}
