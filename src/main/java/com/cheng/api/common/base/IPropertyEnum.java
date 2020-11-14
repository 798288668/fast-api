package com.cheng.api.common.base;

import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * 枚举属性作为存储数据库数据的公共接口
 * @author dhy
 * @version 2019/4/23
 */
public interface IPropertyEnum {

	/**
	 * 枚举定义的属性名称
	 * 注意：实现该类定义属性名称必须为value，否则不生效
	 */
	String DEFAULT_VALUE_NAME = "value";

	/**
	 *  获取枚举属性值
	 * @return 枚举属性值
	 */
	@JsonValue
	default String getValue() {
		Field field = ReflectionUtils.findField(this.getClass(), DEFAULT_VALUE_NAME);
		if (field == null) {
			return null;
		}
		try {
			field.setAccessible(true);
			return field.get(this).toString();
		} catch (IllegalAccessException e) {
			throw new BaseException(e);
		}
	}

	/**
	 * 将属性值对应到枚举
	 * @param enumClass Class<T>
	 * @param value 属性值
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	static <T extends Enum<T>> T valueOfEnum(Class<T> enumClass, String value) {
		if (value == null) {
			throw new IllegalArgumentException("IPropertyEnum value should not be null");
		}
		if (enumClass.isAssignableFrom(IPropertyEnum.class)) {
			throw new IllegalArgumentException("illegal IPropertyEnum type");
		}
		T[] enums = enumClass.getEnumConstants();
		for (T t : enums) {
			IPropertyEnum iPropertyEnum = (IPropertyEnum) t;
			if (iPropertyEnum.getValue().equals(value)) {
				return (T) iPropertyEnum;
			}
		}
		throw new IllegalArgumentException("cannot parse integer: " + value + " to " + enumClass.getName());
	}

}
