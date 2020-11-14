package com.cheng.api.modules.sys.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author fengcheng
 */
@Data
public class TreeNodeDto<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String parentId;
	private List<T> children;

	public static <T extends TreeNodeDto<T>> List<T> buildTree(List<T> treeNodes) {
		boolean existRootNode = false;
		List<T> newTree = new ArrayList<>();
		for (T node : treeNodes) {
			if (isRootNode(node, treeNodes)) {
				newTree.add(findChildren(node, treeNodes));
				existRootNode = true;
			}
		}
		if (!existRootNode) {
			return treeNodes;
		}
		return newTree;
	}

	/**
	 * 判断节点是否是根节点
	 */
	private static <T extends TreeNodeDto<T>> boolean isRootNode(T checkNode, List<T> treeNodes) {
		for (T node : treeNodes) {
			if (Objects.equals(checkNode.getParentId(), node.getId())) {
				return false;
			}
		}
		return true;
	}


	/**
	 * 递归查找子节点
	 */
	private static <T extends TreeNodeDto<T>> T findChildren(T parentNode, List<T> treeNodes) {
		List<T> children = new ArrayList<>();
		for (T node : treeNodes) {
			if (parentNode.getId().equals(node.getParentId())) {
				children.add(findChildren(node, treeNodes));
			}
		}
		parentNode.setChildren(children);
		return parentNode;
	}

}
