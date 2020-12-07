/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.modules.sys.service;

import com.cheng.api.common.base.*;
import com.cheng.api.common.constant.SysConst;
import com.cheng.api.common.constant.SysEnum;
import com.cheng.api.common.util.*;
import com.cheng.api.modules.sys.bean.*;
import com.cheng.api.modules.sys.mapper.SysUserMapper;
import com.cheng.api.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.Weekend;
import tk.mybatis.mapper.weekend.WeekendCriteria;
import tk.mybatis.mapper.weekend.WeekendSqls;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * @author fengcheng
 * @version 2017/2/28
 */
@Slf4j
@Service
public class SysUserServiceImpl implements SysUserService {

	private final SysUserMapper sysUserMapper;

	@Autowired
	public SysUserServiceImpl(SysUserMapper sysUserMapper) {
		this.sysUserMapper = sysUserMapper;
	}

	@Override
	public SysUser get(String id) {
		return sysUserMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<SysRole> getRoleByUserId(String userId) {
		return sysUserMapper.selectRoleByUserId(userId);
	}

	@Override
	public List<SysMenu> getMenuByUserId(String userId) {
		if (Objects.equals(userId, SysConst.USER_SUP_ID)) {
			return sysUserMapper.selectAllMenu();
		}
		return sysUserMapper.selectMenuByUserId(userId);
	}

	@Override
	public SysUser getUserByLoginName(String loginName) {
		SysUser sysUser = new SysUser();
		sysUser.setLoginName(loginName);
		sysUser.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
		return sysUserMapper.selectOne(sysUser);
	}

	private SysUser getUserByUserName(String userName) {
		SysUser sysUser = new SysUser();
		sysUser.setUserName(userName);
		sysUser.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
		return sysUserMapper.selectOne(sysUser);
	}

	@Override
	public PageClient<SysUserListDto> getList(SysUserListQueryDto dto) {
		SysUser current = UserUtils.getUser();

		Weekend<SysUser> weekend = Weekend.of(SysUser.class);
		weekend.orderBy("createDate").desc();
		WeekendCriteria<SysUser, Object> criteria = weekend.weekendCriteria();
		criteria.andEqualTo(DataEntity::getDelFlag, BaseEntity.DEL_FLAG_NORMAL);
		if (current.getPlatform() != SysEnum.Platform.SYSTEM) {
			List<String> userIds = getChildIdsById(current.getId());
			dto.setUserIds(userIds);
			if (userIds != null && !userIds.isEmpty()) {
				criteria.andIn(SysUser::getId, userIds);
			}
		}
		if (StringUtils.isNotBlank(dto.getParentName())) {
			List<SysUser> parents = sysUserMapper.selectByExample(Weekend.of(SysUser.class).weekendCriteria()
					.andEqualTo(SysUser::getUserName, SqlUtil.asLikeStr(dto.getParentName())));
			if (parents != null && !parents.isEmpty()) {
				criteria.andIn(SysUser::getParentId,
						parents.stream().map(BaseEntity::getId).collect(Collectors.toList()));
			}
		}
		if (StringUtils.isNotBlank(dto.getUserName())) {
			criteria.andLike(SysUser::getUserName, SqlUtil.asLikeStr(dto.getUserName()));
		}
		if (dto.getUserType() != null) {
			criteria.andEqualTo(SysUser::getUserType, dto.getUserType());
		}
		if (dto.getPlatform() != null) {
			criteria.andEqualTo(SysUser::getPlatform, dto.getPlatform());
		}
		if (dto.getPlatforms() != null) {
			criteria.andIn(SysUser::getPlatform, dto.getPlatforms());
		}
		if (dto.getUserStatus() != null) {
			criteria.andEqualTo(SysUser::getUserStatus, dto.getUserStatus());
		}
		if (!Objects.equals(current.getId(), SysConst.USER_SUP_ID)) {
			criteria.andNotIn(SysUser::getId, Lists.newArrayList(SysConst.USER_SUP_ID, SysConst.USER_ADMIN_ID));
		}

		dto.startPage();
		List<SysUser> users = sysUserMapper.selectByExample(weekend);
		return PageClient.of(users, SysUserListDto.class);
	}

	@Override
	public List<SysMenu> getMenuByRoleId(String id) {
		return sysUserMapper.selectMenuByRoleId(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result<String> register(SysUserH5AddQueryDto dto) {
		SysUser user = BeanMapper.map(dto, SysUser.class);
		if (UserUtils.getUserByLoginName(user.getLoginName()) != null) {
			return Result.fail(ResultCode.INVALID_LOGIN_NAME_EXIST);
		}
		if (getUserByUserName(user.getUserName()) != null) {
			return Result.fail(ResultCode.INVALID_USER_NAME_EXIST);
		}
		String password = Encodes.encryptPassword(user.getPassword());

		SysUser inviter = get(dto.getInviterId());
		if (inviter.getPlatform() == SysEnum.Platform.FIRST_AGENT
				|| inviter.getPlatform() == SysEnum.Platform.SECOND_AGENT) {
			// 分配邀请人为上级
			user.setParentId(inviter.getId());
		}
		user.setUserType(SysEnum.UserType.ADMIN);

		String roleId;
		if (dto.getPlatform() == SysEnum.Platform.FIRST_AGENT) {
			roleId = SysConst.ROLE_FIRST_AGENT;
		} else if (dto.getPlatform() == SysEnum.Platform.SECOND_AGENT) {
			roleId = SysConst.ROLE_SECOND_AGENT;
		} else {
			return Result.fail(ResultCode.ERROR_PARAM);
		}

		user.setPlatform(dto.getPlatform());
		user.setPassword(password);
		user.setUserStatus(SysEnum.UserStatus.AUDIT);
		user.preInsert(inviter.getId());
		user.setSecret(Encodes.uuid());
		sysUserMapper.insertSelective(user);

		//更新用户角色关联
		sysUserMapper.insertUserRole(user.getId(), roleId.split(","));

		Result<String> result = Result.success();
		result.setMessage(SysConst.SUCCESS_WAIT);
		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result<String> add(SysUserAddQueryDto dto) {
		SysUser current = UserUtils.getUser();
		SysUser user = BeanMapper.map(dto, SysUser.class);
		if (StringUtils.isNotEmpty(user.getId())) {
			// 如果新密码为空，则不更换密码
			if (StringUtils.isNotBlank(user.getPassword())) {
				user.setPassword(Encodes.encryptPassword(user.getPassword()));
			} else {
				user.setPassword(null);
			}
			user.preUpdate(current.getId());
			user.setLoginName(null);
			sysUserMapper.updateByPrimaryKeySelective(user);
			sysUserMapper.deleteUserRoleByUserId(user.getId());
		} else {
			if (UserUtils.getUserByLoginName(user.getLoginName()) != null) {
				return Result.fail(ResultCode.INVALID_LOGIN_NAME_EXIST);
			}
			if (getUserByUserName(user.getUserName()) != null) {
				return Result.fail(ResultCode.INVALID_USER_NAME_EXIST);
			}
			String password;
			if (StringUtils.isNotBlank(user.getPassword())) {
				password = Encodes.encryptPassword(user.getPassword());
			} else {
				password = Encodes.encryptPassword(SysConst.DEFAULT_PASSWORD);
			}

			if (current.getPlatform() == SysEnum.Platform.FIRST_AGENT
					|| current.getPlatform() == SysEnum.Platform.SECOND_AGENT) {
				// 自动分配操作人为上级
				user.setParentId(current.getId());
			}
			user.setPassword(password);
			user.setUserStatus(SysEnum.UserStatus.NORMAL);
			user.preInsert(current.getId());
			user.setSecret(Encodes.uuid());
			sysUserMapper.insertSelective(user);
		}

		//更新用户角色关联
		sysUserMapper.insertUserRole(user.getId(), dto.getRoleId().split(","));
		UserUtils.clearUserCache(user.getId());
		return Result.success(user.getId());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void modifyPassword(String loginName, String password) {
		SysUser user = UserUtils.getUserByLoginName(loginName);
		SysUser sysUser = new SysUser();
		sysUser.setPassword(Encodes.encryptPassword(password));
		sysUser.setUpdateDate(new Date());
		sysUser.setLastPasswordResetDate(new Date());

		Weekend<SysUser> weekend = Weekend.of(SysUser.class);
		WeekendCriteria<SysUser, Object> criteria = weekend.weekendCriteria();
		criteria.andEqualTo(SysUser::getLoginName, loginName);
		sysUserMapper.updateByExampleSelective(sysUser, weekend);

		UserUtils.clearUserCache(user.getId());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result<String> del(String id) {
		SysUser sysUser = sysUserMapper.selectByPrimaryKey(id);
		if (Objects.equals(sysUser.getId(), SysConst.USER_ADMIN_ID) || Objects
				.equals(sysUser.getId(), SysConst.USER_SUP_ID)) {
			throw new BaseException(ResultCode.ERROR_DEL_ADMIN);
		}

		SysUser user = new SysUser();
		user.setId(id);
		user.setDelFlag(BaseEntity.DEL_FLAG_DELETE);
		sysUserMapper.updateByPrimaryKeySelective(user);

		UserUtils.clearUserCache(id);
		return Result.success(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result<String> freeze(String id) {
		SysUser user = new SysUser();
		user.setId(id);
		user.setUserStatus(SysEnum.UserStatus.FREEZE);
		sysUserMapper.updateByPrimaryKeySelective(user);
		UserUtils.clearUserCache(id);
		return Result.success(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result<String> unfreeze(String id) {
		SysUser user = new SysUser();
		user.setId(id);
		user.setUserStatus(SysEnum.UserStatus.NORMAL);
		sysUserMapper.updateByPrimaryKeySelective(user);
		UserUtils.clearUserCache(id);
		return Result.success(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result<String> reset(String id) {
		SysUser user = new SysUser();
		user.setId(id);
		user.setPassword(Encodes.encryptPassword(SysConst.DEFAULT_PASSWORD));
		sysUserMapper.updateByPrimaryKeySelective(user);
		UserUtils.clearUserCache(id);
		return Result.success(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result<String> bindParent(BindParentQueryDto dto) {
		SysUser u = UserUtils.getUser(dto.getId());
		if (u.getPlatform() == SysEnum.Platform.SYSTEM) {
			return Result.fail(ResultCode.ERROR_OPERATE);
		}
		SysUser user = new SysUser();
		user.setId(dto.getId());
		user.setParentId(dto.getParentId());
		sysUserMapper.updateByPrimaryKeySelective(user);
		UserUtils.clearUserCache(dto.getId());
		return Result.success(dto.getId());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result<String> confirm(IdQueryDto dto) {
		SysUser user = get(dto.getId());
		if (user == null || user.getUserStatus() != SysEnum.UserStatus.AUDIT) {
			return Result.fail(ResultCode.ERROR_STATUS);
		}
		user = new SysUser();
		user.setId(dto.getId());
		user.setUserStatus(SysEnum.UserStatus.NORMAL);
		sysUserMapper.updateByPrimaryKeySelective(user);

		UserUtils.clearUserCache(user.getId());
		return Result.success(dto.getId());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result<String> reject(IdQueryDto dto) {
		SysUser user = get(dto.getId());
		if (user == null || user.getUserStatus() != SysEnum.UserStatus.AUDIT) {
			return Result.fail(ResultCode.ERROR_STATUS);
		}
		user = new SysUser();
		user.setId(dto.getId());
		user.setUserStatus(SysEnum.UserStatus.REJECT);
		sysUserMapper.updateByPrimaryKeySelective(user);
		UserUtils.clearUserCache(user.getId());
		return Result.success(dto.getId());
	}

	@Override
	public List<SysUser> getUserbyParentId(String parentId) {
		SysUser user = new SysUser();
		user.setParentId(parentId);
		return sysUserMapper.select(user);
	}

	@Override
	public List<String> getChildIdsById(String userId) {
		List<String> userIds = Lists.newArrayList(userId);
		List<SysUser> twoLevel = getUserbyParentId(userId);
		userIds.addAll(twoLevel.stream().map(BaseEntity::getId).collect(Collectors.toList()));
		twoLevel.forEach(e -> {
			List<SysUser> threeLevel = getUserbyParentId(e.getId());
			userIds.addAll(threeLevel.stream().map(BaseEntity::getId).collect(Collectors.toList()));
		});
		return userIds;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateLoginInfo(String userId, HttpServletRequest request) {
		SysUser user = new SysUser();
		user.setId(userId);
		user.setLoginIp(UserAgentUtils.getIpAddr(request));
		user.setLoginDate(new Date());
		sysUserMapper.updateByPrimaryKeySelective(user);
		UserUtils.clearUserCache(user.getId());
	}

	@Override
	public List<SysUser> getUserByLikeUserName(String userName) {
		return sysUserMapper.selectByExample(Example.builder(SysUser.class)
				.where(WeekendSqls.<SysUser>custom().andLike(SysUser::getUserName, userName)).build());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void resetSecret(String id) {
		SysUser user = new SysUser();
		user.setId(id);
		user.preUpdate(id);
		user.setSecret(Encodes.uuid());
		sysUserMapper.updateByPrimaryKeySelective(user);
		UserUtils.clearUserCache(user.getId());
	}
}
