/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.modules.sys.service;

import com.cheng.api.common.base.PageClient;
import com.cheng.api.common.base.Result;
import com.cheng.api.common.constant.SysEnum;
import com.cheng.api.common.constant.SysEnum;
import com.cheng.api.common.util.BeanMapper;
import com.cheng.api.modules.sys.bean.*;
import com.cheng.api.modules.sys.mapper.SysFeedbackMapper;
import com.cheng.api.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.weekend.Weekend;
import tk.mybatis.mapper.weekend.WeekendCriteria;

import java.util.List;

/**
 * 反馈意见表(SysFeedback)表服务实现类
 *
 * @author fengcheng
 * @since 2019-06-17 20:31:11
 */
@Service
public class SysFeedbackServiceImpl implements SysFeedbackService {

	private final SysFeedbackMapper sysFeedbackMapper;

	public SysFeedbackServiceImpl(SysFeedbackMapper sysFeedbackMapper) {
		this.sysFeedbackMapper = sysFeedbackMapper;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result<String> add(SysFeedbackAddQueryDto dto) {
		SysFeedback feedback = BeanMapper.map(dto, SysFeedback.class);
		feedback.preInsert(UserUtils.getUserId());
		feedback.setStatus(SysEnum.FeedbackStatus.WAIT_DISPOSE);
		feedback.setType(SysEnum.FeedbackType.CONTACT_US);
		feedback.setUserId(feedback.getCreateBy());
		sysFeedbackMapper.insertSelective(feedback);
		return Result.success(feedback.getId());
	}

	@Override
	public PageClient<SysFeedbackDto> list(SysFeedbackListQueryDto dto) {
		Weekend<SysFeedback> weekend = Weekend.of(SysFeedback.class);
		WeekendCriteria<SysFeedback, Object> criteria = weekend.weekendCriteria();
		weekend.orderBy("createDate").desc();
		if (dto.getStatus() != null) {
			criteria.andEqualTo(SysFeedback::getStatus, dto.getStatus());
		}
		dto.startPage();
		List<SysFeedback> feedbacks = sysFeedbackMapper.selectByExample(weekend);
		return PageClient.of(feedbacks, SysFeedbackDto.class);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result<String> dispose(SysFeedbackDisposeQueryDto dto) {
		String userId = UserUtils.getUserId();
		SysFeedback feedback = new SysFeedback();
		feedback.setId(dto.getId());
		feedback.preUpdate(userId);
		feedback.setHandlerId(userId);
		feedback.setHandlerTime(feedback.getUpdateDate());
		feedback.setHandlerInfo(dto.getHandlerInfo());
		feedback.setStatus(SysEnum.FeedbackStatus.DISPOSE);
		sysFeedbackMapper.updateByPrimaryKeySelective(feedback);
		return Result.success(feedback.getId());
	}
}
