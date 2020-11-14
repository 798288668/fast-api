package com.cheng.api.modules.sys.service;

import com.cheng.api.common.constant.SysConst;
import com.cheng.api.common.util.ApiUtils;
import com.cheng.api.common.util.IpUtil;
import com.cheng.api.modules.sys.bean.SysIp;
import com.cheng.api.modules.sys.mapper.SysIpMapper;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * 区域表(SysIp)表服务实现类
 *
 * @author fengcheng
 * @since 2020-05-14 21:54:43
 */
@Service
public class SysIpServiceImpl implements SysIpService {

	private final SysIpMapper sysIpMapper;

	public SysIpServiceImpl(SysIpMapper sysIpMapper) {
		this.sysIpMapper = sysIpMapper;
	}

	@Override
	public void syncIp() {
		String url = "http://ftp.apnic.net/apnic/stats/apnic/delegated-apnic-latest";
		String result = ApiUtils.get(url);
		String[] lists = result.split("\n");
		sysIpMapper.delete(new SysIp());
		List<SysIp> ipList = new ArrayList<>();
		SysIp ip;
		for (String str : lists) {
			if (str.startsWith("apnic") && str.contains("|CN|ipv4|")) {
				String[] a = str.split("\\|");
				int mask = 32 - (int) (Math.log(Double.parseDouble(a[4])) / Math.log(2));
				ip = new SysIp();
				ip.preInsert(SysConst.USER_ADMIN_ID);
				ip.setIpSegment(a[3] + "/" + mask);
				ip.setCountryCode("CN");
				ipList.add(ip);
				if (ipList.size() == 1000) {
					sysIpMapper.insertList(ipList);
					ipList.clear();
				}
			}
		}
		sysIpMapper.insertList(ipList);
	}

	@Override
	public Boolean checkIp(String ip) {
		List<SysIp> ipList = sysIpMapper.selectByExample(Example.builder(SysIp.class).select("ipSegment").build());
		return ipList.parallelStream().anyMatch(e -> IpUtil.isInRange(ip, e.getIpSegment()));
	}

}