/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.common.base;

import com.cheng.api.common.util.BeanMapper;
import com.cheng.api.modules.sys.bean.ExtraDto;
import com.github.pagehelper.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author fengcheng
 * @date 2019-05-30 10:55
 */
@Data
public class PageClient<T> implements Serializable {

	private static final long serialVersionUID = -1L;

	private Pagination pagination;
	private List<T> list;
	private List<String> permission;
	private ExtraDto extra;

	private PageClient() {
	}

	public static <S> PageClient<S> of(List<S> sourceList) {
		Page<S> page = (Page<S>) sourceList;
		PageClient<S> pageClient = new PageClient<>();
		pageClient.setList(sourceList);
		pageClient.setPagination(new Pagination(page.getPageNum(), page.getPageSize(), page.getTotal()));
		return pageClient;
	}

	public static <S, T> PageClient<T> of(List<S> sourceList, Class<T> targetContentClass) {
		Page<S> page = (Page<S>) sourceList;
		PageClient<T> pageClient = new PageClient<>();
		pageClient.setList(BeanMapper.mapList(sourceList, targetContentClass));
		pageClient.setPagination(new Pagination(page.getPageNum(), page.getPageSize(), page.getTotal()));
		return pageClient;
	}

	public static <T> PageClient<T> of(Pagination pagination, List<T> sourceList) {
		PageClient<T> pageClient = new PageClient<>();
		pageClient.setList(sourceList);
		pageClient.setPagination(pagination);
		return pageClient;
	}

	public static <T> PageClient<T> of(Pagination pagination, List<T> sourceList, List<String> permission) {
		PageClient<T> pageClient = new PageClient<>();
		pageClient.setList(sourceList);
		pageClient.setPagination(pagination);
		pageClient.setPermission(permission);
		return pageClient;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Pagination implements Serializable {
		private static final long serialVersionUID = -5986773604148157101L;
		private Integer pageNo;
		private Integer pageSize;
		private Long total;
	}
}
