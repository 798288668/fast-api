/*
 * Copyright (c) 2017. <a href="http://www.lufengc.com">lufengc</a> All rights reserved.
 */

package com.cheng.api.modules.sys.service;

import com.cheng.api.modules.sys.bean.SysDict;
import com.cheng.api.modules.sys.mapper.SysDictMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 字典表service实现类
 *
 * @author fengcheng
 * @version 2016/7/28
 */
@Service
public class SysDictServiceImpl implements SysDictService {

	private final SysDictMapper sysDictMapper;

	public SysDictServiceImpl(SysDictMapper sysDictMapper) {
		this.sysDictMapper = sysDictMapper;
	}

	@Override
	public List<SysDict> getList() {
		return sysDictMapper.selectAll();
	}
}
