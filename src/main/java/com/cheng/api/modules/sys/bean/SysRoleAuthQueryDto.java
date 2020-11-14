package com.cheng.api.modules.sys.bean;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 角色表(SysRole)实体类
 *
 * @author fengcheng
 * @since 2019-07-08 11:15:47
 */
@Data
public class SysRoleAuthQueryDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty
	private String id;
	@NotNull
	private List<String> menuIds;
}
