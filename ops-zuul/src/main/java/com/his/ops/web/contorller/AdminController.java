package com.his.ops.web.contorller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin")
public class AdminController {
	
	//打印日志信息
	private final static Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	//防恶意进入页面
	
	/**
	 * index首页界面
	 */
	@RequestMapping("/index")
	public String index() {
		logger.info("访问主页");
		return "admin/index";
	}
	
	/**
	 * department界面
	 */
	@RequestMapping("/department")
	public String department() {
		logger.info("访问department.jsp页面");
		return "admin/department";
	}

}
