package com.cheng.api.common.util;

import org.springframework.util.StringUtils;

/**
 * sql处理的util
 *
 * @author fengcheng
 * @version 2017-6-13
 */
public class SqlUtil {

	/**
	 * 处理like查询 特殊字符 Title: handleLikeChar Description:
	 */
	public static String replaceSpecialChar4Like(String str) {
		str = StringUtils.replace(str, "'", "''");
		str = StringUtils.replace(str, "[", "[[]");
		str = StringUtils.replace(str, "%", "[%]");
		str = StringUtils.replace(str, "_", "[_]");
		str = StringUtils.replace(str, "^", "[^]");
		return str;
	}

	public static String asLikeStr(String str) {
		return str == null ? "" : "%" + str + "%";
	}

}
