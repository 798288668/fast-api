/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.common.security;

import com.cheng.api.common.config.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @author fengcheng
 * @version 2017/8/8
 */
@Slf4j
public class RequestLimitUtils {
	/**
	 * 缓存前缀
	 */
	private static final String PREFIX = "request_limit_";

	private static RedisTemplate<String, String> redisTemplate = SpringContextHolder.getBean("redisTemplate");

	/**
	 *
	 * @param time 单位时间
	 * @param count 限制次数
	 * @param ip 请求ip
	 * @param request HttpServletRequest
	 */
	public static void requestLimit(Long time, Long count, String ip, HttpServletRequest request) {
		String url = request.getRequestURL().toString();
		String key = PREFIX.concat(url).concat(ip);

		Long hasCount = redisTemplate.opsForValue().increment(key, 1);
		if (hasCount == null) {
			throw new RequestLimitException("方法中缺失HttpServletRequest参数");
		}
		if (hasCount == 1) {
			redisTemplate.expire(key, time, TimeUnit.MILLISECONDS);
		}
		if (hasCount > count) {
			log.info("用户IP[" + ip + "]访问地址[" + url + "]超过了限定的次数[" + count + " / " + time + " ms]");
			throw new RequestLimitException();
		}
	}
}
