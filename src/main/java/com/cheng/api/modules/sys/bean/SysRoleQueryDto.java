package com.cheng.api.modules.sys.bean;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * 角色表(SysRole)实体类
 *
 * @author fengcheng
 * @since 2019-07-08 11:15:47
 */
@Data
public class SysRoleQueryDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	/**
	 * 角色名称
	 */
	@NotEmpty
	private String name;
	/**
	 * 英文名称
	 */
	@NotEmpty
	private String enname;
	/**
	 * 菜单
	 */
	private List<String> menuIds;
}
