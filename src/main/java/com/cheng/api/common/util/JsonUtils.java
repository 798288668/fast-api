/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.common.util;

import com.cheng.api.common.base.BaseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fengcheng
 * @version 2017/7/11
 */
public class JsonUtils {

	private JsonUtils() {
	}

	static ObjectMapper mapper = new ObjectMapper();

	public static <T> T toObject(String json, Class<T> valueType) {
		try {
			return mapper.readValue(json, valueType);
		} catch (Exception e) {
			throw new BaseException(e);
		}
	}

	public static String toJson(Object value) {
		try {
			return mapper.writeValueAsString(value);
		} catch (JsonProcessingException e) {
			throw new BaseException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static Map<String, String> toMap(String json) {
		try {
			return mapper.readValue(json, HashMap.class);
		} catch (Exception e) {
			throw new BaseException(e);
		}
	}


	//	/**
	//	 * json属性过滤（过滤默认的基础属性）
	//	 *
	//	 * @param object 要过滤的对象
	//	 * @return 过滤后的json对象
	//	 */
	//	public static Object propertiesfilter(Object object) {
	//		PropertyFilter filter = (ob, key, value) -> !("remarks,createBy,updateBy,updateDate,delFlag,"
	//				+ "pageNum,pageSize,orderBy,token").contains(key);
	//		String s = JSON.toJSONString(object, filter, SerializerFeature.WriteMapNullValue);
	//		return JSON.parse(s);
	//	}
	//
	//	/**
	//	 * json属性过滤，过滤掉指定的不需要的属性
	//	 *
	//	 * @param object     要过滤的对象
	//	 * @param properties 指定要过滤的属性
	//	 * @return 过滤后的json对象
	//	 */
	//	public static Object propertiesfilter(Object object, String properties) {
	//		PropertyFilter filter = (ob, key, value) -> !(properties).contains(key);
	//		String s = JSON.toJSONString(object, filter, SerializerFeature.WriteMapNullValue);
	//		return JSON.parse(s);
	//	}
	//
	//	/**
	//	 * json属性
	//	 *
	//	 * @param object     要过滤的对象
	//	 * @param properties 指定要过滤的属性
	//	 * @param exclude    是否排除，true：排除指定属性，false：输出指定的属性（与过滤相反） ，默认false
	//	 * @return 过滤后的json对象
	//	 */
	//	public static Object propertiesfilter(Object object, String properties, boolean exclude) {
	//		if (exclude) {
	//			return propertiesfilter(object, properties);
	//		} else {
	//			PropertyFilter filter = (ob, key, value) -> properties.contains(key);
	//			String s = JSON.toJSONString(object, filter, SerializerFeature.WriteMapNullValue);
	//			return JSON.parse(s);
	//		}
	//	}
}
