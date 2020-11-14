/*
 * Copyright (c) 2017. <a href="http://www.lufengc.com">lufengc</a> All rights reserved.
 */

package com.cheng.api.common.config;

import com.cheng.api.common.constant.RedisConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author fengcheng
 * @version 2016/11/18
 */
@Slf4j
@Component
public class StartupRunner implements CommandLineRunner {

	private final RedisTemplate<String, Object> redisTemplate;
	private final StringRedisTemplate stringRedisTemplate;
	private final SystemProperties systemProperties;

	@Autowired
	public StartupRunner(RedisTemplate<String, Object> redisTemplate, StringRedisTemplate stringRedisTemplate,
			SystemProperties systemProperties) {
		this.redisTemplate = redisTemplate;
		this.stringRedisTemplate = stringRedisTemplate;
		this.systemProperties = systemProperties;
	}

	@Override
	public void run(String... args) {
		RedisUtil.setRedisTemplate(redisTemplate);
		RedisUtil.setStringRedisTemplate(stringRedisTemplate);
		if (systemProperties.isStartCleanCache()) {
			RedisUtil.clear(RedisConst.PREFIX);
		}
		log.info("所有缓存数据已清除");
	}
}
