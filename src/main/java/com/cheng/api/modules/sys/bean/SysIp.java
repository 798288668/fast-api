package com.cheng.api.modules.sys.bean;

import com.cheng.api.common.base.DataEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 区域表(SysIp)实体类
 *
 * @author fengcheng
 * @since 2020-05-14 21:54:43
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysIp extends DataEntity {
	private static final long serialVersionUID = 1L;
	/**
	 * 名称
	 */
	private String ipSegment;
	/**
	 * 排序
	 */
	private String countryCode;

}