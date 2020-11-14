/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.common.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author fengcheng
 * @version 2019-06-17
 */
public class RandomUtil {
	private RandomUtil() {
	}

	private static String FORMAT = "0.00";
	private static Random random = new Random();
	private static final String BASE_NUMBER = "0123456789";
	private static final String BASE_CHAR = "abcdefghijklmnopqrstuvwxyz";

	private static final String BASE_CHAR_NUMBER = "abcdefghijklmnopqrstuvwxyz0123456789";

	public static String getIdCard() {
		String[] provinces = new String[]{"11", "12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "34", "35",
				"36", "37", "41", "42", "43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63", "64",
				"65", "71", "81", "82"};
		String no = (new Random()).nextInt(899) + 100 + "";
		String[] checks = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "X"};
		String builder =
				randomOne(provinces) + randomCityCode(18) + randomCityCode(28) + randomBirth(20, 50) + no + randomOne(
						checks);
		return builder;
	}

	private static String randomOne(String[] s) {
		return s[(new Random()).nextInt(s.length - 1)];
	}

	private static String randomCityCode(int max) {
		int i = (new Random()).nextInt(max) + 1;
		return i > 9 ? i + "" : "0" + i;
	}

	private static String randomBirth(int minAge, int maxAge) {
		SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
		Calendar date = Calendar.getInstance();
		date.setTime(new Date());
		int randomDay = 365 * minAge + (new Random()).nextInt(365 * (maxAge - minAge));
		date.set(5, date.get(5) - randomDay);
		return dft.format(date.getTime());
	}

	public static int randomInt(int min, int max) {
		return random.nextInt(max - min) + min;
	}

	public static int randomInt(int limit) {
		return random.nextInt(limit);
	}

	public static int randomInt() {
		return random.nextInt();
	}

	public static long randomLong() {
		return random.nextLong();
	}

	public static long randomLong(long min, long max) {
		return min + (long) ((new Random()).nextDouble() * (double) (max - min));
	}

	public static String randomString(int length) {
		return randomString("abcdefghijklmnopqrstuvwxyz0123456789", length);
	}

	public static String randomNumbers(int length) {
		return randomString("0123456789", length);
	}

	public static String randomString(String baseString, int length) {
		StringBuilder sb = new StringBuilder();
		if (length < 1) {
			length = 1;
		}

		int baseLength = baseString.length();

		for (int i = 0; i < length; ++i) {
			int number = random.nextInt(baseLength);
			sb.append(baseString.charAt(number));
		}

		return sb.toString();
	}

	public static double randomDouble(double min, double max) {
		return min + (max - min) * random.nextDouble();
	}

	public static double randomDouble() {
		return randomDouble(0.0D, 100.0D);
	}

	public static String randomDouble(String format) {
		return (new DecimalFormat(format)).format(randomDouble());
	}

}
