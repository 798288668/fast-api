/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.modules.sys.bean;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author fengcheng
 * @version 2017/2/28
 */
@Data
public class IdQueryDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotEmpty
	private String id;
	private String type;
}
