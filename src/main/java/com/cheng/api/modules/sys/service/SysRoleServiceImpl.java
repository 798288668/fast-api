package com.cheng.api.modules.sys.service;

import com.cheng.api.common.base.*;
import com.cheng.api.common.constant.SysEnum;
import com.cheng.api.common.util.BeanMapper;
import com.cheng.api.common.util.StringUtils;
import com.cheng.api.modules.sys.bean.*;
import com.cheng.api.modules.sys.mapper.SysRoleMapper;
import com.cheng.api.modules.sys.mapper.SysUserMapper;
import com.cheng.api.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.weekend.Weekend;
import tk.mybatis.mapper.weekend.WeekendCriteria;

import java.util.List;

/**
 * 角色表(SysRole)表服务实现类
 *
 * @author fengcheng
 * @since 2019-07-08 11:15:47
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

	private final SysRoleMapper sysRoleMapper;
	private final SysUserMapper sysUserMapper;

	public SysRoleServiceImpl(SysRoleMapper sysRoleMapper, SysUserMapper sysUserMapper) {
		this.sysRoleMapper = sysRoleMapper;
		this.sysUserMapper = sysUserMapper;
	}

	@Override
	public SysRole get(String id) {
		return sysRoleMapper.selectByPrimaryKey(id);
	}

	@Override
	public PageClient<SysRoleDto> getList(BaseQueryDto dto) {
		Weekend<SysRole> weekend = Weekend.of(SysRole.class);
		weekend.orderBy("createDate").desc();
		WeekendCriteria<SysRole, Object> criteria = weekend.weekendCriteria();
		criteria.andEqualTo(DataEntity::getDelFlag, BaseEntity.DEL_FLAG_NORMAL);
		dto.startPage();
		List<SysRole> sysRoles = sysRoleMapper.selectByExample(weekend);
		return PageClient.of(sysRoles, SysRoleDto.class);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result<String> add(SysRoleQueryDto dto) {
		SysRole role = BeanMapper.map(dto, SysRole.class);
		if (StringUtils.isNotEmpty(dto.getId())) {
			role.preUpdate(UserUtils.getUserId());
			sysRoleMapper.updateByPrimaryKeySelective(role);
			sysUserMapper.deleteRoleMenuByRoleId(role.getId());
		} else {
			role.preInsert(UserUtils.getUserId());
			role.setIsSys(SysEnum.IsSys.NO);
			sysRoleMapper.insertSelective(role);
		}
		if (dto.getMenuIds() != null) {
			//保存角色权限关联表
			sysUserMapper.insertRoleMenu(role.getId(), dto.getMenuIds());
		}
		return Result.success(role.getId());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result<String> del(String id) {
		SysRole role = new SysRole();
		role.setId(id);
		role.setDelFlag(BaseEntity.DEL_FLAG_DELETE);
		sysRoleMapper.updateByPrimaryKeySelective(role);

		List<SysUser> users = sysUserMapper.selectUserByRoleId(id);
		users.forEach(e -> UserUtils.clearUserCache(e.getId()));
		return Result.success(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result<String> authorize(SysRoleAuthQueryDto dto) {
		sysUserMapper.deleteRoleMenuByRoleId(dto.getId());
		sysUserMapper.insertRoleMenu(dto.getId(), dto.getMenuIds());

		List<SysUser> users = sysUserMapper.selectUserByRoleId(dto.getId());
		users.forEach(e -> UserUtils.clearUserCache(e.getId()));
		return Result.success(dto.getId());
	}
}
