/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.modules.sys.service;

import com.cheng.api.common.base.PageClient;
import com.cheng.api.common.base.Result;
import com.cheng.api.modules.sys.bean.SysFeedbackAddQueryDto;
import com.cheng.api.modules.sys.bean.SysFeedbackDisposeQueryDto;
import com.cheng.api.modules.sys.bean.SysFeedbackDto;
import com.cheng.api.modules.sys.bean.SysFeedbackListQueryDto;

/**
 * 反馈意见表(SysFeedback)表服务接口
 *
 * @author fengcheng
 * @since 2019-06-17 20:31:11
 */
public interface SysFeedbackService {

	Result<String> add(SysFeedbackAddQueryDto dto);

	PageClient<SysFeedbackDto> list(SysFeedbackListQueryDto dto);

	Result<String> dispose(SysFeedbackDisposeQueryDto dto);
}
