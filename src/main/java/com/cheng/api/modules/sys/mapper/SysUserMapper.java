/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.modules.sys.mapper;

import com.cheng.api.common.base.CommonMapper;
import com.cheng.api.modules.sys.bean.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author fengcheng
 * @version 2017/2/28
 */
@Mapper
@Repository
public interface SysUserMapper extends CommonMapper<SysUser> {

	List<SysRole> selectRoleByUserId(String userId);

	List<SysMenu> selectMenuByUserId(String userId);

	List<SysMenu> selectMenuByRoleId(String roleId);

	List<SysMenu> selectAllMenu();

	void deleteUserRoleByUserId(String userId);

	void insertUserRole(@Param("userId") String userId, @Param("roleIds") String[] roleIds);

	void deleteRoleMenuByRoleId(String roleId);

	void insertRoleMenu(@Param("roleId") String roleId, @Param("menuIds") List<String> menuIds);

	void insertVendorCoder(@Param("vendorId") String vendorId, @Param("coderIds") List<String> coderIds);

	void deleteVendorCoderByVendorId(String vendorId);

	List<SysUser> selectCoderByVendorId(String vendorId);

	List<SysUser> getMemberByVendorId(String vendorId);

	UserFundDto selectStat(@Param("userId") String userId, @Param("userIds") List<String> userIds,
			@Param("vendorIds") List<String> vendorIds);

	List<SysUser> selectUserByRoleId(String roleId);

	List<SysUser> selectList(SysUserListQueryDto dto);
}
