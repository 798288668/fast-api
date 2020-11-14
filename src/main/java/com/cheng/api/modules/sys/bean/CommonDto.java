/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.modules.sys.bean;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author fengcheng
 * @version 2017/2/28
 */
@Data
public class CommonDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String version;
	private Date currentTime;
	private Map<String, List<SysDictDto>> dict;
}
