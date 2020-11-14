/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.common.base;

import com.cheng.api.common.util.StringUtils;
import com.github.pagehelper.PageHelper;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author fengcheng
 * @version 2017/2/28
 */
@Data
public class BaseQueryDto implements Serializable {

	private static final long serialVersionUID = -1L;

	/**
	 * 当前页
	 */
	private Integer pageNo;
	/**
	 * 每页条数
	 */
	private Integer pageSize;
	/**
	 * 排序
	 */
	private String orderBy;

	/**
	 * 开始分页
	 * 返回Page分页源数据信息，后续第一天select sql即为分支的sql
	 * 执行sql后该Page对象会被注入分页的全部信息，则使用该page对象构建PageClient
	 */
	public void startPage() {
		PageHelper.startPage(pageNo != null ? pageNo : 1, pageSize != null ? pageSize : 10);
	}

	public String getOrderBy() {
		if (StringUtils.isNotBlank(this.orderBy)) {
			String[] split = this.orderBy.split("_");
			if (split.length > 1) {
				this.orderBy = split[0] + " " + (Objects.equals(split[1], "descend") ? "DESC" : "ASC");
			}
		}
		return this.orderBy;
	}
}
