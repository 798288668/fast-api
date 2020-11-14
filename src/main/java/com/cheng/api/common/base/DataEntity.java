/*
 * Copyright &copy; cc All rights reserved.
 */
package com.cheng.api.common.base;

import com.cheng.api.common.util.Encodes;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * 数据Entity类
 *
 * @author fengcheng
 * @version 2017/2/28
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DataEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 备注
	 */
	protected String remarks;
	/**
	 * 创建者
	 */
	protected String createBy;
	/**
	 * 创建日期
	 */
	protected Date createDate;
	/**
	 * 更新者
	 */
	protected String updateBy;
	/**
	 * 更新日期
	 */
	protected Date updateDate;
	/**
	 * 删除标记（0：正常；1：删除；）
	 */
	protected String delFlag;

	/**
	 * 插入之前执行方法，需要手动调用
	 */
	public void preInsert(String userId) {
		setId(Encodes.uuid());
		if (StringUtils.isNotBlank(userId)) {
			this.updateBy = userId;
			this.createBy = userId;
		}
		this.updateDate = new Date();
		this.createDate = this.updateDate;
		this.delFlag = DEL_FLAG_NORMAL;
	}

	/**
	 * 更新之前执行方法，需要手动调用
	 */
	public void preUpdate(String userId) {
		if (StringUtils.isNotBlank(userId)) {
			this.updateBy = userId;
		}
		this.updateDate = new Date();
	}
}
