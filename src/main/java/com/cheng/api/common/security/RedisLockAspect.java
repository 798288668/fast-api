/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.common.security;

import com.cheng.api.common.base.BaseException;
import com.cheng.api.common.base.ResultCode;
import com.cheng.api.common.config.RedisUtil;
import com.cheng.api.common.util.Reflections;
import com.cheng.api.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @author fengcheng
 * @version 2017/4/17
 */
@Slf4j
@Order(1)
@Aspect
@Configuration
public class RedisLockAspect {

	@Pointcut("@annotation(com.cheng.api.common.security.RedisLock)")
	public void lockPointcut() {
	}


	@Around("lockPointcut()")
	public Object lockAround(ProceedingJoinPoint joinPoint) throws Throwable {
		// 获取注解
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		RedisLock redisLock = signature.getMethod().getAnnotation(RedisLock.class);

		// 获取注解参数
		Object keyParam = joinPoint.getArgs()[redisLock.paramIndex()];
		if (redisLock.paramInner()) {
			String paramInnerName = redisLock.paramInnerName();
			// paramInnerName 不能为空
			if (StringUtils.isEmpty(paramInnerName)) {
				throw new BaseException(ResultCode.FAILD_PARAM);
			}
			keyParam = Reflections.invokeGetter(keyParam, paramInnerName);
		}

		String lockKey = redisLock.key().concat(keyParam.toString());
		String lockValue = RandomStringUtils.randomNumeric(8);
		boolean tryLock = false;
		try {
			// 尝试加锁
			tryLock = RedisUtil.tryLock(lockKey, lockValue, redisLock.timeout());
			if (tryLock) {
				// 继续执行业务
				return joinPoint.proceed();
			} else {
				throw new BaseException(ResultCode.REQUEST_REPEAT);
			}
		} finally {
			// 释放锁
			if (tryLock) {
				RedisUtil.releaseLock(lockKey, lockValue);
			}
		}
	}

}
