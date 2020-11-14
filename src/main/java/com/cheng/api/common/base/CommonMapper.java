/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.common.base;

import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertUseGeneratedKeysMapper;

/**
 * @author fengcheng
 * @version 2017/2/28
 */
public interface CommonMapper<T> extends Mapper<T>, InsertListMapper<T>, InsertUseGeneratedKeysMapper<T> {
}
