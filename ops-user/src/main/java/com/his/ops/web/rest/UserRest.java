package com.his.ops.web.rest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.his.ops.entity.UserEntity;
import com.his.ops.entity.UserInfoEntity;
import com.his.ops.service.UserInfoService;
import com.his.ops.service.UserService;

@RestController
@RequestMapping("user")
public class UserRest {
	
	//打印日志信息
	private final static Logger LOG = LoggerFactory.getLogger(UserRest.class);

	@Autowired
	UserService userService;
	
	@Autowired
	private UserInfoService userInfoService;
	

	@RequestMapping("/insert")
	public int insert(UserEntity record) {
		//用户信息表
		String imgPath = "";
		String username = record.getUsername();
		
		UserInfoEntity userInfoBean = new UserInfoEntity();
		userInfoBean.setUsername(username);
		userInfoBean.setHeadPortraits(imgPath);
		
		userInfoService.insert(userInfoBean);
		int result = userService.insert(record);
		LOG.info("新增用户-->"+record);
		return result;
	}

	@RequestMapping("/selectUserByUsername")
	public UserEntity selectUserByUsername(String username) {
		UserEntity userBean = userService.selectUserByUsername(username);
		LOG.info("根据用户名查找用户-->"+userBean);
		return userBean;
	}
	
}
