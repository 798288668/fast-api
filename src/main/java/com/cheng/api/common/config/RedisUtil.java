package com.cheng.api.common.config;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author jck
 * @version 1.0
 * @since 2017/2/8
 */
public class RedisUtil {
	private RedisUtil() {
	}

	private static RedisTemplate<String, Object> redisTemplate;

	private static StringRedisTemplate stringRedisTemplate;

	public static void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		RedisUtil.redisTemplate = redisTemplate;
	}

	public static void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
		RedisUtil.stringRedisTemplate = stringRedisTemplate;
	}

	/**
	 * 设置缓存
	 *
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时 单位秒
	 */
	public static void setObject(String key, Object value, long cacheSeconds) {
		ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
		if (0 == cacheSeconds) {
			valueOperations.set(key, value);
		} else {
			valueOperations.set(key, value, cacheSeconds, TimeUnit.SECONDS);
		}

	}

	/**
	 * 获取缓存
	 *
	 * @param key 键
	 * @return 值
	 */
	public static Object getObject(String key) {
		ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
		return valueOperations.get(key);
	}

	/**
	 * 获取Redis缓存数据，此方法支持检测获取出来的对象是否是指定数据类型或者其子类或实现类，
	 * 如果不是则说明存储的类型和目标预知类型不是同一个，此时返回空对象。
	 *
	 * @param key 键
	 * @param targetType Class<?>
	 * @return Object
	 */
	public static Object getObject(String key, Class<?> targetType) {
		Object object = getObject(key);
		if (Objects.nonNull(object)) {
			Class<?> dataType = object.getClass();
			if (dataType == List.class) {
				// List -> Element
				dataType = ((List<?>) object).get(0).getClass();
			} else if (dataType == Map.class) {
				// Map -> Value
				dataType = ((Map<?, ?>) object).entrySet().iterator().next().getClass();
			}
			// If the types do not match, delete the cache and return a null value
			if (dataType != Object.class && dataType != targetType && !targetType.isAssignableFrom(dataType)) {
				object = null;
				delObject(key);
			}
		}
		return object;
	}

	/**
	 * 删除缓存
	 *
	 * @param key 键
	 */
	public static void delObject(String key) {
		redisTemplate.delete(key);
	}

	/**
	 * 检查key是否已经存在
	 *
	 * @param key 键
	 */
	public static Boolean exists(String key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * 设置缓存
	 *
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 */
	public static void setString(String key, String value, long cacheSeconds) {
		ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
		if (0 == cacheSeconds) {
			valueOperations.set(key, value);
		} else {
			valueOperations.set(key, value, cacheSeconds, TimeUnit.SECONDS);
		}

	}

	/**
	 * 获取缓存
	 *
	 * @param key 键
	 * @return 值
	 */
	public static String getString(String key) {
		ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
		return valueOperations.get(key);

	}

	/**
	 * 清空redis
	 */
	public static void clear() {
		String script = "redis.call('flushdb')";
		List<String> keys = new ArrayList<>(0);
		stringRedisTemplate.execute(new DefaultRedisScript<>(script, Void.class), keys, "");
	}

	/**
	 * 清空redis
	 */
	public static void clear(String keyPrefix) {
		stringRedisTemplate.delete(Objects.requireNonNull(stringRedisTemplate.keys(keyPrefix + "*")));
	}

	/**
	 * 尝试添加分布式锁
	 *
	 * @param lockKey        加锁key
	 * @param lockValue      加锁值
	 * @param timeoutSeconds 锁过期时间/秒
	 * @return true：加锁成功 fasle：加锁失败
	 */
	public static Boolean tryLock(String lockKey, String lockValue, int timeoutSeconds) {
		String script = "if redis.call('EXISTS', KEYS[1]) == 0 then redis.call('SETEX', KEYS[1], ARGV[2], ARGV[1]); return true else return false end";
		List<String> keys = new ArrayList<>(1);
		keys.add(lockKey);
		return stringRedisTemplate.execute(new DefaultRedisScript<>(script, Boolean.class), keys, lockValue,
				String.valueOf(timeoutSeconds));
	}

	/**
	 * 释放分布式锁
	 *
	 * @param lockKey   加锁key
	 * @param lockValue 加锁值
	 */
	public static void releaseLock(String lockKey, String lockValue) {
		String script = "if redis.call('GET', KEYS[1]) == ARGV[1] then redis.call('DEL', KEYS[1]) end";
		List<String> keys = new ArrayList<>(1);
		keys.add(lockKey);
		stringRedisTemplate.execute(new DefaultRedisScript<>(script, Void.class), keys, lockValue);
	}
}
