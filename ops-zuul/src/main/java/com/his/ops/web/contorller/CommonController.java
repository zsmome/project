package com.his.ops.web.contorller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/common")
public class CommonController {

	@RequestMapping("/userInfo")
	public String userInfo() {
		return "common/userInfo";
	}
	
}
