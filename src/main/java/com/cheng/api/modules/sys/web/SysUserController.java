/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.modules.sys.web;

import com.cheng.api.common.base.PageClient;
import com.cheng.api.common.base.Result;
import com.cheng.api.common.base.ResultCode;
import com.cheng.api.common.constant.MenuConst;
import com.cheng.api.common.constant.SysEnum;
import com.cheng.api.common.util.StringUtils;
import com.cheng.api.modules.sys.bean.*;
import com.cheng.api.modules.sys.service.SysUserService;
import com.cheng.api.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * 用户
 * @author fengcheng
 * @version 2017/4/17
 */
@Validated
@RestController
@RequestMapping("/sys/user/")
public class SysUserController {

	private final SysUserService sysUserService;

	@Autowired
	public SysUserController(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	/**
	 * 获取用户
	 */
	@PostMapping("list")
	@PreAuthorize("hasAuthority('" + MenuConst.SYS_USER_LIST + "')")
	public Result<PageClient<SysUserListDto>> list(@Validated @RequestBody SysUserListQueryDto dto) {
		String userId = UserUtils.getUserId();
		dto.setUserType(SysEnum.UserType.ADMIN);
		dto.setPlatform(SysEnum.Platform.SYSTEM);
		PageClient<SysUserListDto> result = sysUserService.getList(dto);
		result.getList().parallelStream().forEach(e -> {
			SysRole role = UserUtils.getRole(e.getId());
			e.setRoleId(role.getId());
			e.setRole(role.getName());
			e.setPermission(UserUtils.getButtonsWithTag(userId, MenuConst.SYS_USER_EDIT, MenuConst.SYS_USER_DEL,
					MenuConst.SYS_USER_FREEZE, MenuConst.SYS_USER_RESET));
		});
		result.setPermission(UserUtils.getButtonsWithTag(userId, MenuConst.SYS_USER_ADD));
		return Result.success(result);
	}

	/**
	 * 增加用户
	 */
	@PostMapping("add")
	@PreAuthorize("hasAuthority('" + MenuConst.SYS_USER_ADD + "')")
	public Result<String> add(@Validated @RequestBody SysUserAddQueryDto dto) {
//		dto.setUserType(SysEnum.UserType.ADMIN);
		dto.setPlatform(SysEnum.Platform.SYSTEM);
		return sysUserService.add(dto);
	}

	/**
	 * 删除用户
	 */
	@PostMapping("del")
	@PreAuthorize("hasAuthority('" + MenuConst.SYS_USER_DEL + "')")
	public Result<String> del(@Validated @RequestBody IdQueryDto dto) {
		return sysUserService.del(dto.getId());
	}

	/**
	 * 冻结
	 */
	@PostMapping("freeze")
	@PreAuthorize("hasAuthority('" + MenuConst.SYS_USER_FREEZE + "')")
	public Result<String> freeze(@Validated @RequestBody IdQueryDto dto) {
		return sysUserService.freeze(dto.getId());
	}

	/**
	 * 解冻
	 */
	@PostMapping("unfreeze")
	@PreAuthorize("hasAuthority('" + MenuConst.SYS_USER_FREEZE + "')")
	public Result<String> unfreeze(@Validated @RequestBody IdQueryDto dto) {
		return sysUserService.unfreeze(dto.getId());
	}

	/**
	 * 重置密码
	 */
	@PostMapping("reset")
	@PreAuthorize("hasAuthority('" + MenuConst.SYS_USER_RESET + "')")
	public Result<String> reset(@Validated @RequestBody IdQueryDto dto) {
		return sysUserService.reset(dto.getId());
	}

}
