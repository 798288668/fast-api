/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.modules.sys.web;

import com.cheng.api.modules.sys.utils.DictUtils;
import com.cheng.api.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author fengcheng
 * @version 2017/10/21
 */
@Controller
public class IndexController {

	/**
	 * 首页
	 */
	@GetMapping("/")
	public String index() {
		return "index";
	}

	/**
	 * 文档信息
	 */
	@GetMapping("/register")
	public String register(String inviterId, String platform, Model model) {
		model.addAttribute("inviterId", inviterId);
		model.addAttribute("inviter", UserUtils.getUser(inviterId).getUserName());
		model.addAttribute("platform", platform);
		model.addAttribute("platformName", DictUtils.getDictLabel(platform, "sys_user_platform", ""));
		return "register";
	}
}
