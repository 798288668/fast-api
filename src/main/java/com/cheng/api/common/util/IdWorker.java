/*
 * Copyright &copy; cc All rights reserved.
 */
package com.cheng.api.common.util;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * <p>
 * 高效GUID产生算法(sequence),基于Snowflake实现64位自增ID算法。 <br>
 * 优化开源项目 http://git.oschina.net/yu120/sequence
 * </p>
 *
 * @author hubin
 * @version 2016-08-01
 */
public class IdWorker {
	private IdWorker() {
	}

	/**
	 * 主机和进程的机器码
	 */
	private static final Sequence worker = new Sequence();
	private static final SecureRandom random = new SecureRandom();

	public static long getId() {
		return worker.nextId();
	}

	/**
	 * <p>
	 * 获取去掉"-" UUID
	 * </p>
	 */
	public static synchronized String uuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 使用SecureRandom随机生成Long.
	 */
	public static long randomLong() {
		return Math.abs(random.nextLong());
	}

	/**
	 * 基于Base62编码的SecureRandom随机生成bytes.
	 */
	public static String randomBase62(int length) {
		byte[] randomBytes = new byte[length];
		random.nextBytes(randomBytes);
		return Encodes.encodeBase62(randomBytes);
	}
}
