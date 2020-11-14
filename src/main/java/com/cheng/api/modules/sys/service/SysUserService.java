/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.modules.sys.service;


import com.cheng.api.common.base.PageClient;
import com.cheng.api.common.base.Result;
import com.cheng.api.modules.sys.bean.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author fengcheng
 * @version 2017/4/17
 */
public interface SysUserService {

	SysUser get(String id);

	List<SysRole> getRoleByUserId(String userId);

	List<SysMenu> getMenuByUserId(String userId);

	SysUser getUserByLoginName(String loginName);

	void modifyPassword(String loginName, String password);

	PageClient<SysUserListDto> getList(SysUserListQueryDto dto);

	List<SysMenu> getMenuByRoleId(String id);

	Result<String> register(SysUserH5AddQueryDto dto);

	Result<String> add(SysUserAddQueryDto dto);

	Result<String> del(String id);

	Result<String> freeze(String id);

	Result<String> unfreeze(String id);

	Result<String> reset(String id);

	Result<String> bindParent(BindParentQueryDto dto);

	Result<String> confirm(IdQueryDto dto);

	Result<String> reject(IdQueryDto dto);

	List<SysUser> getUserbyParentId(String parentId);

	List<String> getChildIdsById(String userId);

	void updateLoginInfo(String userId, HttpServletRequest request);

	List<SysUser> getUserByLikeUserName(String userName);

	void resetSecret(String id);
}
