/*
 * Copyright &copy; cc All rights reserved.
 */
package com.cheng.api.common.util;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 简单封装Dozer, 实现深度转换Bean<->Bean的Mapper.实现:
 * <p>
 * 1. 持有Mapper的单例.
 * 2. 返回值类型转换.
 * 3. 批量转换Collection中的所有对象.
 * 4. 区分创建新的B对象与将对象A值复制到已存在的B对象两种函数.
 *
 * @author calvin
 * @version 2013-01-15
 */
@Slf4j
public class BeanMapper {
	private BeanMapper() {
	}

	/**
	 * 持有Dozer单例, 避免重复创建DozerMapper消耗资源.
	 */
	private static final DozerBeanMapper DOZER = new DozerBeanMapper();

	/**
	 * 基于Dozer转换对象的类型.
	 */
	public static <T> T map(Object source, Class<T> destinationClass) {
		if (source == null) {
			return null;
		}
		return DOZER.map(source, destinationClass);
	}

	/**
	 * 基于Dozer转换Collection中对象的类型.
	 */
	@SuppressWarnings("rawtypes")
	public static <T> List<T> mapList(Collection sourceList, Class<T> destinationClass) {
		List<T> destinationList = Lists.newArrayList();
		for (Object sourceObject : sourceList) {
			T destinationObject = DOZER.map(sourceObject, destinationClass);
			destinationList.add(destinationObject);
		}
		return destinationList;
	}

	/**
	 * 基于Dozer将对象A的值拷贝到对象B中.
	 */
	public static void copy(Object source, Object destinationObject) {
		DOZER.map(source, destinationObject);
	}

	/**
	 * map 转 bean
	 */
	public static <T> T mapToBean(Map<String, ?> map, Class<T> destinationClass) {
		return DOZER.map(map, destinationClass);
	}

	/**
	 * bean 转map
	 */
	public static Map<String, Object> beanToMap(Object source) {
		HashMap<String, Object> map = new HashMap<>(16);
		DOZER.map(source, map);
		return map;
	}

	/**
	 * bean 转map
	 */
	public static Map<String, String> beanToMapForStringValue(Object source) {
		HashMap<String, String> map = new HashMap<>(16);
		DOZER.map(source, map);
		return map;
	}
}
