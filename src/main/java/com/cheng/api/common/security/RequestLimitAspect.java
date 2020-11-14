/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.common.security;

import com.cheng.api.common.util.UserAgentUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @author fengcheng
 * @version 2017/8/8
 */
@Slf4j
@Aspect
@Component
public class RequestLimitAspect {
	/**
	 * 缓存前缀
	 */
	private static final String PREFIX = "request_limit_";
	/**
	 * RedisTemplate
	 */
	private final RedisTemplate<String, String> redisTemplate;

	@Autowired
	public RequestLimitAspect(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * Request limit.
	 *
	 * @param joinPoint the join point
	 * @param limit     the limit
	 * @throws RequestLimitException the request limit exception
	 */
	@Before("within(@org.springframework.web.bind.annotation.RestController * || @org.springframework.stereotype.Controller *) && @annotation(limit)")
	public void requestLimit(final JoinPoint joinPoint, RequestLimit limit) {
		Object[] args = joinPoint.getArgs();
		HttpServletRequest request = null;
		for (Object arg : args) {
			if (arg instanceof HttpServletRequest) {
				request = (HttpServletRequest) arg;
				break;
			}
		}
		if (request == null) {
			throw new RequestLimitException("方法中缺失HttpServletRequest参数");
		}
		String ip = UserAgentUtils.getIpAddr(request);
		String url = request.getRequestURL().toString();
		String key = PREFIX.concat(url).concat(ip);

		Long count = redisTemplate.opsForValue().increment(key, 1);
		if (count == null) {
			throw new RequestLimitException("方法中缺失HttpServletRequest参数");
		}
		if (count == 1) {
			redisTemplate.expire(key, limit.time(), TimeUnit.MILLISECONDS);
		}
		if (count > limit.count()) {
			String error = "用户IP[" + ip + "]访问地址[" + url + "]超过了限定的次数[" + limit.count() + " / " + limit.time() + " ms]";
			throw new RequestLimitException(error);
		}
	}
}
