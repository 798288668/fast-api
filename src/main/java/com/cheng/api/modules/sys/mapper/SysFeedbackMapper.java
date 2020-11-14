/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.modules.sys.mapper;

import com.cheng.api.common.base.CommonMapper;
import com.cheng.api.modules.sys.bean.SysFeedback;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 反馈意见表(SysFeedback)表数据库访问层
 *
 * @author fengcheng
 * @since 2019-06-17 20:31:12
 */
@Mapper
@Repository
public interface SysFeedbackMapper extends CommonMapper<SysFeedback> {

}
