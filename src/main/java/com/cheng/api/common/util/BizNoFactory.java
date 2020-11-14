package com.cheng.api.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author fengcheng
 * @version 2019-07-15
 */
public class BizNoFactory {
	private BizNoFactory() {
	}

	private static final String ORDER_NO = "DD";
	private static final String CODE_NO = "GM";
	private static final String PAY_NO = "ZF";
	/** 随即编码 */
	private static final int[] R = new int[]{7, 9, 6, 2, 8, 1, 3, 0, 5, 4};
	/** 用户id和随机数总长度 */
	private static final int MAX_LENGTH = 6;

	/**
	 * 生成时间戳
	 */
	private static String getDateTime() {
		DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return sdf.format(new Date());
	}

	/**
	 * 生成固定长度随机码
	 */
	private static long getRandom() {
		long min = 1;
		long max = 9;
		for (int i = 1; i < (long) BizNoFactory.MAX_LENGTH; i++) {
			min *= 10;
			max *= 10;
		}
		return ((long) (new Random().nextDouble() * (max - min))) + min;
	}

	/**
	 * 生成不带类别标头的编码
	 */
	private static synchronized String getCode() {
		return getDateTime() + getRandom();
	}

	/**
	 * 生成订单单号编码
	 */
	public static String getOrderNo() {
		return ORDER_NO + getCode();
	}

	/**
	 * 生成订单单号编码
	 */
	public static String getCodeNo() {
		return CODE_NO + getCode();
	}

	/**
	 * 生成订单单号编码
	 */
	public static String getPayNo() {
		return PAY_NO + getCode();
	}
}
