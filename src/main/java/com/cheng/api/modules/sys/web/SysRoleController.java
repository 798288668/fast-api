package com.cheng.api.modules.sys.web;

import com.cheng.api.common.base.BaseEntity;
import com.cheng.api.common.base.BaseQueryDto;
import com.cheng.api.common.base.PageClient;
import com.cheng.api.common.base.Result;
import com.cheng.api.common.constant.MenuConst;
import com.cheng.api.common.constant.SysConst;
import com.cheng.api.common.constant.SysEnum;
import com.cheng.api.common.util.BeanMapper;
import com.cheng.api.modules.sys.bean.*;
import com.cheng.api.modules.sys.service.SysRoleService;
import com.cheng.api.modules.sys.service.SysUserService;
import com.cheng.api.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 角色表(SysRole)表控制层
 *
 * @author fengcheng
 * @since 2019-07-08 11:15:47
 */
@RestController
@RequestMapping("/sys/role/")
public class SysRoleController {

	private final SysRoleService sysRoleService;
	private final SysUserService sysUserService;

	public SysRoleController(SysRoleService sysRoleService, SysUserService sysUserService) {
		this.sysRoleService = sysRoleService;
		this.sysUserService = sysUserService;
	}

	/**
	 * 获取菜单
	 */
	@PostMapping("menu")
	@PreAuthorize("hasAuthority('" + MenuConst.SYS_ROLE_AUTHORIZE + "')")
	public Result<List<SysMenuDto>> menu() {
		List<SysMenu> menus = UserUtils.getMenuList(UserUtils.getUserId());
		List<SysMenuDto> dtos = BeanMapper.mapList(menus, SysMenuDto.class);
		List<SysMenuDto> treeNodes = SysMenu.recursive(dtos, SysMenu.getRootId());
		return Result.success(treeNodes);
	}

	/**
	 * 获取角色
	 */
	@PostMapping("list")
	@PreAuthorize("hasAuthority('" + MenuConst.SYS_ROLE_LIST + "')")
	public Result<PageClient<SysRoleDto>> list(@Validated @RequestBody BaseQueryDto dto) {
		SysUser current = UserUtils.getUser();
		PageClient<SysRoleDto> page = sysRoleService.getList(dto);
		List<SysRoleDto> result = page.getList();
		if (dto.getPageSize() == 100) {
			result = result.stream().filter(e -> e.getIsSys() == SysEnum.IsSys.NO).collect(Collectors.toList());
		}
		result.parallelStream().forEach(e -> {
			e.setMenuIds(sysUserService.getMenuByRoleId(e.getId()).stream().map(BaseEntity::getId)
					.collect(Collectors.toList()));
			ArrayList<String> tags = Lists.newArrayList();
			tags.add(MenuConst.SYS_ROLE_AUTHORIZE);
			if (SysConst.USER_SUP_ID.equals(current.getId())) {
				tags.add(MenuConst.SYS_ROLE_EDIT);
				tags.add(MenuConst.SYS_ROLE_DEL);
			}
			if (e.getIsSys() == SysEnum.IsSys.NO) {
				tags.add(MenuConst.SYS_ROLE_EDIT);
				tags.add(MenuConst.SYS_ROLE_DEL);
			}
			if (SysConst.USER_ADMIN_ID.equals(current.getId()) && Objects.equals(e.getId(), "id_sys")) {
				tags.remove(MenuConst.SYS_ROLE_AUTHORIZE);
			}
			e.setPermission(UserUtils.getButtonsWithTag(current.getId(), tags));
		});
		return Result.success(PageClient.of(page.getPagination(), result,
				UserUtils.getButtonsWithTag(current.getId(), MenuConst.SYS_ROLE_ADD)));
	}

	/**
	 * 增加
	 */
	@PostMapping("add")
	@PreAuthorize("hasAuthority('" + MenuConst.SYS_ROLE_ADD + "')")
	public Result<String> add(@Validated @RequestBody SysRoleQueryDto dto) {
		return sysRoleService.add(dto);
	}

	/**
	 * 删除
	 */
	@PostMapping("del")
	@PreAuthorize("hasAuthority('" + MenuConst.SYS_ROLE_DEL + "')")
	public Result<String> del(@Validated @RequestBody IdQueryDto dto) {
		return sysRoleService.del(dto.getId());
	}

	/**
	 * 授权
	 */
	@PostMapping("authorize")
	@PreAuthorize("hasAuthority('" + MenuConst.SYS_ROLE_AUTHORIZE + "')")
	public Result<String> authorize(@Validated @RequestBody SysRoleAuthQueryDto dto) {
		return sysRoleService.authorize(dto);
	}
}
