package com.his.ops.web.rest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.his.ops.entity.UserInfoEntity;
import com.his.ops.service.UserInfoService;

@RestController
@RequestMapping("userInfo")
public class UserInfoRest {
	
	//打印日志信息
	private final static Logger LOG = LoggerFactory.getLogger(UserInfoRest.class);

	@Autowired
	UserInfoService userInfoService;
	

	@RequestMapping("/updateUserInfoByUsername")
	public int updateUserInfoByUsername(
			@RequestParam("username") String username,
			@RequestParam("headPortraits") String headPortraits) {
		UserInfoEntity record = new UserInfoEntity();
		record.setUsername(username);
		record.setHeadPortraits(headPortraits);
		int result = userInfoService.updateUserInfoByUsername(record);
		LOG.info("修改用户信息-->"+record);
		return result;
	}

	@RequestMapping("/selectUserInfoByUsername")
	public UserInfoEntity selectUserInfoByUsername(String username) {
		UserInfoEntity userInfoBean = userInfoService.selectUserInfoByUsername(username);
		LOG.info("根据用户名查找用户信息-->"+userInfoBean);
		return userInfoBean;
	}

}
