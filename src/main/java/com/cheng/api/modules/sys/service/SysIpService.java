package com.cheng.api.modules.sys.service;

/**
 * 区域表(SysIp)表服务接口
 *
 * @author fengcheng
 * @since 2020-05-14 21:54:43
 */
public interface SysIpService {


	void syncIp();

	Boolean checkIp(String ip);
}