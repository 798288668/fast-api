/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.modules.sys.web;

import com.cheng.api.common.base.PageClient;
import com.cheng.api.common.base.Result;
import com.cheng.api.common.constant.SysEnum;
import com.cheng.api.common.constant.MenuConst;
import com.cheng.api.common.constant.SysEnum;
import com.cheng.api.modules.sys.bean.*;
import com.cheng.api.modules.sys.service.SysFeedbackService;
import com.cheng.api.modules.sys.utils.UserUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 反馈意见表(SysFeedback)表控制层
 *
 * @author fengcheng
 * @since 2019-06-17 20:31:12
 */
@Validated
@RestController
@RequestMapping("/sys/feedback/")
public class SysFeedbackController {

	private final SysFeedbackService sysFeedbackService;

	public SysFeedbackController(SysFeedbackService sysFeedbackService) {
		this.sysFeedbackService = sysFeedbackService;
	}

	/**
	 * 联系客服
	 */
	@PostMapping("add")
	public Result<String> add(@Validated @RequestBody SysFeedbackAddQueryDto dto) {
		return sysFeedbackService.add(dto);
	}

	/**
	 * 联系客服
	 */
	@PostMapping("feedback/list")
	@PreAuthorize("hasAuthority('" + MenuConst.SYS_FEEDBACK_LIST + "')")
	public Result<PageClient<SysFeedbackDto>> list(@Validated @RequestBody SysFeedbackListQueryDto dto) {
		String userId = UserUtils.getUserId();
		PageClient<SysFeedbackDto> result = sysFeedbackService.list(dto);
		result.getList().parallelStream().forEach(e -> {
			SysUser user = UserUtils.getUser(e.getUserId());
			e.setUserName(user.getUserName());
			e.setLoginName(user.getLoginName());
			if (e.getStatus() == SysEnum.FeedbackStatus.WAIT_DISPOSE) {
				e.setPermission(UserUtils.getButtonsWithTag(userId, MenuConst.SYS_FEEDBACK_DISPOSE));
			}
		});
		return Result.success(result);
	}

	/**
	 * 处理
	 */
	@PostMapping("feedback/dispose")
	@PreAuthorize("hasAuthority('" + MenuConst.SYS_FEEDBACK_DISPOSE + "')")
	public Result<String> dispose(@Validated @RequestBody SysFeedbackDisposeQueryDto dto) {
		return sysFeedbackService.dispose(dto);

	}
}
