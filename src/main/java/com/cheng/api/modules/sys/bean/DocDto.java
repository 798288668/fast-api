package com.cheng.api.modules.sys.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 * @author fengcheng
 * @version 2019-07-24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userId;
	private String apiUrl;
	private String key;
	private String domain;
}
