package com.cheng.api.modules.sys.web;

import com.cheng.api.modules.sys.service.SysIpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author fengcheng
 * @version 2019-07-25
 */
@Slf4j
@Component
public class ScheduledTasks {

	private final SysIpService sysIpService;

	public ScheduledTasks(SysIpService sysIpService) {
		this.sysIpService = sysIpService;
	}

	/**
	 * 每周一五点
	 */
	@Scheduled(cron = "0 0 5 ? * MON")
	public void syncIp() {
		try {
			sysIpService.syncIp();
		} catch (Exception ex) {
			log.error("更新ip，自动处理定时任务异常", ex);
		}
	}

}
