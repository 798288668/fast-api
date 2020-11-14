/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.modules.sys.web;

import org.springframework.stereotype.Controller;
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
}
