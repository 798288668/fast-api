package com.cheng.api.modules.sys.bean;

import com.cheng.api.common.base.DataEntity;
import com.cheng.api.common.constant.SysEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色表(SysRole)实体类
 *
 * @author fengcheng
 * @since 2019-07-08 11:15:47
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysRole extends DataEntity {
	private static final long serialVersionUID = 1L;
	/**
	 * 归属机构
	 */
	private String officeId;
	/**
	 * 角色名称
	 */
	private String name;
	/**
	 * 英文名称
	 */
	private String enname;
	/**
	 * 角色类型
	 */
	private String roleType;
	/**
	 * 数据范围
	 */
	private String dataScope;
	/**
	 * 是否系统数据
	 */
	private SysEnum.IsSys isSys;
	/**
	 * 是否可用
	 */
	private String useable;
	/**
	 * 平台
	 */
	private SysEnum.Platform platform;


}
