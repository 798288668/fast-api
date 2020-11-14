/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.modules.sys.bean;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author fengcheng
 * @version 2017/2/28
 */
@Data
public class IdsQueryDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<String> ids;
}
