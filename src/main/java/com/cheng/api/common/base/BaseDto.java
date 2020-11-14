/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.common.base;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author fengcheng
 * @version 2017/2/28
 */
@Data
public class BaseDto implements Serializable {

	private static final long serialVersionUID = -1L;

	private String id;

	private List<String> permission;

}
