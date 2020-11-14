/*
 * Copyright (c) 2017. <a href="http://www.lufengc.com">lufengc</a> All rights reserved.
 */

package com.cheng.api.modules.sys.mapper;

import com.cheng.api.common.base.CommonMapper;
import com.cheng.api.modules.sys.bean.SysDict;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author fengcheng
 * @version 2017/2/28
 */
@Mapper
@Repository
public interface SysDictMapper extends CommonMapper<SysDict> {

}
