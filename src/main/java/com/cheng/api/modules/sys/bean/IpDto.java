/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.modules.sys.bean;


import lombok.Data;

import java.io.Serializable;

/**
 * @author fengcheng
 * @version 2017/2/28
 */
@Data
public class IpDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String code;
	private IpDto.IpInfo data;

	@Data
	public static class IpInfo {
		private String ip;
		private String country_id;
		private String country;
		private String region;
		private String area_id;
		private String area;
		private String city_id;
		private String city;
		private String county_id;
		private String county;
		private String region_id;
		private String isp_id;
		private String isp;
	}
}
