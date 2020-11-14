/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.common.security;

import com.cheng.api.common.base.BaseException;
import com.cheng.api.common.base.ResultCode;
import com.cheng.api.common.config.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author fengcheng
 * @version 2017/4/17
 */
@Slf4j
public class RedisLockUtil {


	/**
	 * 执行锁
	 * @param lockKey redis key
	 * @param timeout 锁过期时间/秒
	 * @param bizNo 唯一业务号
	 */
	public static boolean isLock(String lockKey, Integer timeout, String bizNo) {
		String lockValue = RandomStringUtils.randomNumeric(8);
		boolean tryLock = false;
		try {
			// 尝试加锁
			tryLock = RedisUtil.tryLock(lockKey + bizNo, lockValue, timeout);
			if (tryLock) {
				// 继续执行业务
				return true;
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
