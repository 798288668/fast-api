/*
 * Copyright (c) 2017. <a href="http://www.lufengc.com">lufengc</a> All rights reserved.
 */

package com.cheng.api.modules.sys.utils;

import com.cheng.api.common.config.RedisUtil;
import com.cheng.api.common.config.SpringContextHolder;
import com.cheng.api.common.constant.RedisConst;
import com.cheng.api.common.util.StringUtils;
import com.cheng.api.modules.sys.bean.SysDict;
import com.cheng.api.modules.sys.bean.SysDictDto;
import com.cheng.api.modules.sys.service.SysDictService;
import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典工具类
 *
 * @author fengcheng
 * @version 2016/7/28
 */
public class DictUtils {

	private DictUtils() {
	}

	private static SysDictService dictService = SpringContextHolder.getBean(SysDictService.class);

	public static String getDictLabel(String value, String type, String defaultValue) {
		if (!StringUtils.isAnyEmpty(type, value)) {
			return getDictList(type).parallelStream().filter(dict -> value.equals(dict.getValue())).findFirst()
					.map(SysDictDto::getLabel).orElse(defaultValue);
		}
		return defaultValue;
	}

	public static List<SysDictDto> getDictList(String type) {
		List<SysDictDto> dictList = getDictMap().get(type);
		return dictList == null ? Lists.newArrayList() : dictList;
	}

	/**
	 * 获取字典MAP
	 *
	 * @return Map
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, List<SysDictDto>> getDictMap() {
		List<SysDictDto> dictList;
		Map<String, List<SysDictDto>> dictMap = (Map<String, List<SysDictDto>>) RedisUtil
				.getObject(RedisConst.DIC_LIST);
		if (dictMap == null) {
			dictMap = new HashMap<>(128);
			List<SysDict> list = dictService.getList();
			for (SysDict dict : list) {
				dictList = dictMap.get(dict.getType());
				SysDictDto build = SysDictDto.builder().label(dict.getLabel()).value(dict.getValue()).build();
				if (dictList != null) {
					dictList.add(build);
				} else {
					dictMap.put(dict.getType(), Lists.newArrayList(build));
				}
			}
			RedisUtil.setObject(RedisConst.DIC_LIST, dictMap, 0);
		}
		return dictMap;
	}
}
