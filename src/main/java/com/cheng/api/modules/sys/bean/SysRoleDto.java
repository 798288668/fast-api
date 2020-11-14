package com.cheng.api.modules.sys.bean;

import com.cheng.api.common.base.BaseDto;
import com.cheng.api.common.constant.SysEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 角色表(SysRole)实体类
 *
 * @author fengcheng
 * @since 2019-07-08 11:15:47
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysRoleDto extends BaseDto {
	private static final long serialVersionUID = 1L;
	/**
	 * 角色名称
	 */
	private String name;
	/**
	 * 英文名称
	 */
	private String enname;
	/**
	 * 菜单
	 */
	private List<String> menuIds;
	/**
	 * 创建日期
	 */
	protected Date createDate;
	/**
	 * 是否系统数据
	 */
	private SysEnum.IsSys isSys;
}
