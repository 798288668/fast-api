/*
 * Copyright &copy; cc All rights reserved.
 */
package com.cheng.api.modules.sys.bean;

import com.cheng.api.common.base.DataEntity;
import com.cheng.api.common.constant.SysEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * 菜单Entity
 *
 * @author fengcheng
 * @version 2016/7/28
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysMenu extends DataEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 父级菜单
	 */
	private String parentId;
	/**
	 * 所有父级编号
	 */
	private String parentIds;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 链接
	 */
	private String href;
	/**
	 * 目标（ mainFrame、_blank、_self、_parent、_top）
	 */
	private String target;
	/**
	 * 图标
	 */
	private String icon;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 0菜单1按钮
	 */
	private SysEnum.MenuType type;
	/**
	 * 权限标识
	 */
	private String permission;

	public static void sort(List<SysMenu> sourcelist) {
		sourcelist.sort(Comparator.comparingInt(SysMenu::getSort));
	}

	public static void sortList(List<SysMenu> list, List<SysMenu> sourcelist, String parentId, boolean cascade) {
		for (SysMenu e : sourcelist) {
			if (Objects.equals(e.getParentId(), parentId)) {
				list.add(e);
				if (cascade) {
					//判断是否还有子节点, 有则继续获取子节点
					for (SysMenu child : sourcelist) {
						if (child.getParentId().equals(e.getId())) {
							sortList(list, sourcelist, e.getId(), true);
							break;
						}
					}
				}
			}
		}
	}

	public static <T extends TreeNodeDto<T>> List<T> recursive(List<T> nodes, String parentId) {
		List<T> wrapper = new ArrayList<>();
		for (Iterator<T> ite = nodes.iterator(); ite.hasNext(); ) {
			T node = ite.next();
			if (Objects.equals(node.getParentId(), parentId)) {
				wrapper.add(node);
				ite.remove(); // 减少下一次循环次数
				List<T> children = recursive(new ArrayList<>(nodes), node.getId());
				if (CollectionUtils.isNotEmpty(children)) {
					node.setChildren(children);
				}
			}
		}
		return wrapper;
	}

	public static String getRootId() {
		return "1";
	}

}
