/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.common.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author fengcheng
 * @version 2017/4/17
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RedisLock {

	/**
	 * redis key
	 */
	String key();

	/**
	 * 锁过期时间/秒
	 */
	int timeout() default 5;

	/**
	 * 方法参数的位置，从0开始
	 */
	int paramIndex();

	/**
	 * 是否从参数内部取值
	 */
	boolean paramInner() default false;

	/**
	 * 参数内部取值的参数名称
	 */
	String paramInnerName() default "";

}
