/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Hex、Base64编码和解码
 *
 * @author fengcheng
 * @version 2016-01-15 09:56:22
 */
@Slf4j
public class Encodes {

	public static final String SECRET = "aff4af70cc0d6570257dba443285a50de32d6f60";

	private static final String DEFAULT_URL_ENCODING = "UTF-8";
	private static final char[] BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;


	/**
	 * 生成签名
	 */
	public static String createSignForString(Map<String, String> params, String key) {
		StringBuilder sb = new StringBuilder();
		// 去掉参数值为空的，并按照参数名ASCII码从小到大排序（字典序）
		Map<String, String> sortMap = new LinkedHashMap<>();
		params.entrySet().stream().filter(e -> StringUtils.isNotEmpty(e.getValue())).sorted(Map.Entry.comparingByKey())
				.forEachOrdered(e -> sortMap.put(e.getKey(), e.getValue()));
		// 遍历排序的字典,并拼接"key=value"格式
		for (Map.Entry<String, String> entry : sortMap.entrySet()) {
			String value = entry.getValue().trim();
			if (StringUtils.isNotEmpty(value)) {
				sb.append("&").append(entry.getKey()).append("=").append(value);
			}
		}
		String stringA = sb.toString().replaceFirst("&", "");
		String stringSignTemp = stringA + key;
		// MD5 32位小写 加密
		String sign = DigestUtils.md5Hex(stringSignTemp);
		log.info(stringSignTemp + " -> " + sign);
		return sign;
	}

	/**
	 * 生成签名
	 */
	public static String createSignWithJson(Map<String, String> params, String key) {

		// 去掉参数值为空的，并按照参数名ASCII码从小到大排序（字典序）
		Map<String, String> sortMap = new LinkedHashMap<>();
		params.entrySet().stream().filter(e -> StringUtils.isNotEmpty(e.getValue())).sorted(Map.Entry.comparingByKey())
				.forEachOrdered(e -> sortMap.put(e.getKey(), e.getValue()));
		// 拼接上key
		sortMap.put("key", key);
		// 参数转成json字符串
		String stringSignTemp = JsonUtils.toJson(sortMap);
		// MD5 32位小写 加密
		String sign = DigestUtils.md5Hex(stringSignTemp);
		log.info(stringSignTemp + " -> " + sign);
		return sign;
	}

	/**
	 * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
	 */
	public static String encryptPassword(String plainPassword) {
		if (plainPassword == null) {
			return null;
		}
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
		return Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword);
	}

	/**
	 * 验证密码
	 *
	 * @param plainPassword 明文密码
	 * @param password      密文密码
	 * @return 验证成功返回true
	 */
	public static boolean validatePassword(String plainPassword, String password) {
		if (StringUtils.isBlank(plainPassword)) {
			return false;
		}
		byte[] salt = Encodes.decodeHex(password.substring(0, SALT_SIZE * 2));
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(StandardCharsets.UTF_8), salt, HASH_INTERATIONS);
		return password.equals(Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword));
	}

	/**
	 * Hex编码.
	 */
	public static String encodeHex(byte[] input) {
		return new String(Hex.encodeHex(input));
	}

	/**
	 * Hex解码.
	 */
	public static byte[] decodeHex(String input) {
		try {
			return Hex.decodeHex(input.toCharArray());
		} catch (DecoderException e) {
			throw Exceptions.unchecked(e);
		}
	}


	/**
	 * Base64编码.
	 */
	public static String encodeBase64(byte[] input) {
		return new String(Base64.encodeBase64(input));
	}


	/**
	 * Base64编码.
	 */
	public static String encodeBase64(String input) {
		try {
			return new String(Base64.encodeBase64(input.getBytes(DEFAULT_URL_ENCODING)));
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	/**
	 * Base64编码, URL安全(将Base64中的URL非法字符'+'和'/'转为'-'和'_', 见RFC3548).
	 */
	public static byte[] encodeUrlSafeBase64(byte[] input) {
		return Base64.encodeBase64URLSafe(input);
	}

	/**
	 * Base64解码.
	 */
	public static byte[] decodeBase64(String input) {
		return Base64.decodeBase64(input.getBytes());
	}

	/**
	 * Base64解码.
	 */
	public static String decodeBase64String(String input) {
		try {
			return new String(Base64.decodeBase64(input.getBytes()), DEFAULT_URL_ENCODING);
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	/**
	 * Base62编码.
	 */
	public static String encodeBase62(byte[] input) {
		char[] chars = new char[input.length];
		for (int i = 0; i < input.length; i++) {
			chars[i] = BASE62[((input[i] & 0xFF) % BASE62.length)];
		}
		return new String(chars);
	}

	/**
	 * Html 转码.
	 */
	public static String escapeHtml(String html) {
		return StringEscapeUtils.escapeHtml4(html);
	}


	/**
	 * Html 解码.
	 */
	public static String unescapeHtml(String htmlEscaped) {
		return StringEscapeUtils.unescapeHtml4(htmlEscaped);
	}


	/**
	 * Xml 转码.
	 */
	public static String escapeXml(String xml) {
		return StringEscapeUtils.escapeXml10(xml);
	}


	/**
	 * Xml 解码.
	 */
	public static String unescapeXml(String xmlEscaped) {
		return StringEscapeUtils.unescapeXml(xmlEscaped);
	}


	/**
	 * URL 编码, Encode默认为UTF-8.
	 */
	public static String urlEncode(String part) {
		try {
			return URLEncoder.encode(part, DEFAULT_URL_ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw Exceptions.unchecked(e);
		}
	}

	/**
	 * URL 解码, Encode默认为UTF-8.
	 */
	public static String urlDecode(String part) {

		try {
			return URLDecoder.decode(part, DEFAULT_URL_ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw Exceptions.unchecked(e);
		}
	}

	/**
	 * md5加密
	 */
	public static String md5Hex(String value) {
		return DigestUtils.md5Hex(value);
	}

	/**
	 * sha1加密
	 */
	public static String sha1Hex(String value) {
		return DigestUtils.sha1Hex(value);
	}

	/**
	 * sha256加密
	 */
	public static String sha256Hex(String value) {
		return DigestUtils.sha256Hex(value);
	}

	/**
	 * 产生一个36个字符的UUID
	 */

	public static String randomUuid() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * sha1加密
	 *
	 * @param text 需要加密的文本
	 * @return 加密后的字符串
	 */
	public static String sha1(String text) {
		String s;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			byte[] result = digest.digest(text.getBytes());
			s = new String(Hex.encodeHex(result));
		} catch (NoSuchAlgorithmException e) {
			s = "";
		}
		return s;
	}
}
