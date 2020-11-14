package com.cheng.api.modules.sys.service;

import com.cheng.api.common.base.BaseQueryDto;
import com.cheng.api.common.base.PageClient;
import com.cheng.api.common.base.Result;
import com.cheng.api.modules.sys.bean.SysRole;
import com.cheng.api.modules.sys.bean.SysRoleAuthQueryDto;
import com.cheng.api.modules.sys.bean.SysRoleDto;
import com.cheng.api.modules.sys.bean.SysRoleQueryDto;

/**
 * 角色表(SysRole)表服务接口
 *
 * @author fengcheng
 * @since 2019-07-08 11:15:47
 */
public interface SysRoleService {


	SysRole get(String id);

	PageClient<SysRoleDto> getList(BaseQueryDto dto);

	Result<String> add(SysRoleQueryDto dto);

	Result<String> del(String id);

	Result<String> authorize(SysRoleAuthQueryDto dto);
}
