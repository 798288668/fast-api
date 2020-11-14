/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.modules.sys.utils;

import com.cheng.api.common.base.UnauthorizedException;
import com.cheng.api.common.config.RedisUtil;
import com.cheng.api.common.config.SpringContextHolder;
import com.cheng.api.common.constant.RedisConst;
import com.cheng.api.common.constant.SysEnum;
import com.cheng.api.common.security.JwtUser;
import com.cheng.api.common.util.StringUtils;
import com.cheng.api.modules.sys.bean.SysMenu;
import com.cheng.api.modules.sys.bean.SysRole;
import com.cheng.api.modules.sys.bean.SysUser;
import com.cheng.api.modules.sys.service.SysUserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.cheng.api.common.base.ResultCode.INVALID_TOKEN;

/**
 * @author fengcheng
 * @version 2017/8/15
 */
public class UserUtils {

	private UserUtils() {
	}

	private static final SysUserService sysUserService = SpringContextHolder.getBean(SysUserService.class);
	private static final RedisTemplate<String, String> REDIS_TEMPLATE = SpringContextHolder.getBean("redisTemplate");

	public static final String USER_CACHE_ID = RedisConst.PREFIX + "userCache_id_";
	public static final String USER_CACHE_LOGIN_NAME = RedisConst.PREFIX + "userCache_loginName_";
	public static final String USER_CACHE_MENU_LIST = RedisConst.PREFIX + "userCache_menu_id_";
	public static final String USER_CACHE_ROLE_LIST = RedisConst.PREFIX + "userCache_role_id_";
	public static final String USER_ACCOUNT_CACHE_ID = RedisConst.PREFIX + "userAccountCache_id_";

	/**
	 * 校验验证码
	 *
	 * @return 用户信息
	 */
	public static boolean checkCaptcha(String captchaCode, String captchaValue) {
		String cacheCaptchaValue = "";
		//验证码校验
		if (captchaCode != null) {
			cacheCaptchaValue = REDIS_TEMPLATE.opsForValue().get(captchaCode);
			REDIS_TEMPLATE.delete(captchaCode);
		}
		return captchaValue != null && cacheCaptchaValue != null && Objects
				.equals(cacheCaptchaValue.toLowerCase(), captchaValue.toLowerCase());
	}

	/**
	 * 获取当前登录者ID
	 *
	 * @return 当前用户id
	 */
	public static String getUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		try {
			JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
			return jwtUser.getId();
		} catch (Exception e) {
			throw new UnauthorizedException(INVALID_TOKEN);
		}
	}

	/**
	 * 获取当前用户信息
	 *
	 * @return 用户信息
	 */
	public static SysUser getUser() {
		return getUser(getUserId());
	}

	/**
	 * 获取用户信息
	 *
	 * @return 用户信息
	 */
	public static SysUser getUserByLoginName(String loginName) {
		SysUser sysUser = (SysUser) RedisUtil.getObject(USER_CACHE_LOGIN_NAME + loginName);
		if (sysUser == null) {
			sysUser = sysUserService.getUserByLoginName(loginName);
			if (sysUser != null) {
				sysUser.setSysRoleList(sysUserService.getRoleByUserId(sysUser.getId()));
				sysUser.setSysMenuList(sysUserService.getMenuByUserId(sysUser.getId()));
				RedisUtil.setObject(USER_CACHE_LOGIN_NAME + loginName, sysUser, 0);
			}
		}
		return sysUser;
	}

	/**
	 * 获取用户信息
	 *
	 * @return 用户信息
	 */
	public static SysUser getUser(String userId) {
		SysUser sysUser = (SysUser) RedisUtil.getObject(USER_CACHE_ID + userId);
		if (sysUser == null) {
			sysUser = sysUserService.get(userId);
			RedisUtil.setObject(USER_CACHE_ID + userId, sysUser, 0);
		}
		return sysUser != null ? sysUser : new SysUser();
	}

	/**
	 * 获取
	 *
	 * @return 用户信息
	 */
	public static SysRole getRole(String userId) {
		List<SysRole> roleList = getRoleList(userId);
		if (roleList != null && !roleList.isEmpty()) {
			return roleList.get(0);
		}
		return new SysRole();
	}

	/**
	 * 获取用户信息
	 *
	 * @return 用户信息
	 */
	@SuppressWarnings("unchecked")
	public static List<SysRole> getRoleList(String userId) {
		List<SysRole> roleList = (List<SysRole>) RedisUtil.getObject(USER_CACHE_ROLE_LIST + userId);
		if (roleList == null) {
			roleList = sysUserService.getRoleByUserId(userId);
			if (roleList != null) {
				RedisUtil.setObject(USER_CACHE_ROLE_LIST + userId, roleList, 0);
			}
		}
		return roleList;
	}

	/**
	 * 获取当前用户授权菜单
	 *
	 * @return 权限菜单列表
	 */
	@SuppressWarnings("unchecked")
	public static List<SysMenu> getMenuList(String userId) {
		List<SysMenu> menuList = (List<SysMenu>) RedisUtil.getObject(USER_CACHE_MENU_LIST + userId);
		if (menuList == null) {
			menuList = sysUserService.getMenuByUserId(userId);
			if (menuList != null) {
				SysMenu.sort(menuList);
				RedisUtil.setObject(USER_CACHE_MENU_LIST + userId, menuList, 0);
			}
		}
		return menuList;
	}

	public static List<String> getButtonsWithTag(String userId, List<String> tags) {
		return getButtonsWithTag(userId, tags.toArray(new String[0]));
	}

	public static List<String> getButtonsWithTag(String userId, String... tag) {
		List<SysMenu> menuList = getMenuList(userId);
		if (menuList != null) {
			return menuList.stream()
					.filter(e -> e.getType() == SysEnum.MenuType.BUTTON && !Objects.equals(e.getParentId(), "0")
							&& StringUtils.contains(Arrays.toString(tag), e.getPermission()))
					.map(SysMenu::getPermission).collect(Collectors.toList());
		} else {
			return new ArrayList<>();
		}
	}

	/**
	 * 清除指定用户缓存
	 */
	public static void clearUserCache(String userId) {
		SysUser user = getUser(userId);
		RedisUtil.delObject(USER_CACHE_ID + user.getId());
		RedisUtil.delObject(USER_CACHE_LOGIN_NAME + user.getLoginName());
		RedisUtil.delObject(USER_CACHE_MENU_LIST + user.getId());
		RedisUtil.delObject(USER_CACHE_ROLE_LIST + user.getId());
		RedisUtil.delObject(USER_ACCOUNT_CACHE_ID + user.getId());

	}
}
