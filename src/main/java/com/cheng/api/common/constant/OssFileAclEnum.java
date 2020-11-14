package com.cheng.api.common.constant;

import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author fengL
 **/
@ToString
@Getter
public enum OssFileAclEnum {

	/**
	 * 默认权限
	 */
	Default("default", "默认"),
	/**
	 * 私有读
	 */
	Private("private", "私有读写"),
	/**
	 * 公有读私有写
	 */
	PublicRead("public-read", "公有读私有写"),
	/**
	 * 公共读写
	 */
	PublicReadWrite("public-read-write", "公共读写");

	/**
	 * 权限编码
	 */
	private final String code;
	/**
	 * 权限描述
	 */
	private final String description;

	OssFileAclEnum(String code, String description) {
		this.code = code;
		this.description = description;
	}

	/**
	 * 通过code获取枚举元素
	 */
	public static OssFileAclEnum get(String code) {
		List<OssFileAclEnum> ossFileAclEnumList = Arrays.stream(OssFileAclEnum.values())
				.filter(x -> Objects.equals(x.code, code)).collect(Collectors.toList());
		if (!ossFileAclEnumList.isEmpty()) {
			return ossFileAclEnumList.get(0);
		} else {
			return null;
		}
	}

}
