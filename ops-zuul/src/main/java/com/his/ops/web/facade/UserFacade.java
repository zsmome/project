package com.his.ops.web.facade;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.his.ops.dto.ResponseInfo;
import com.his.ops.entity.UserEntity;

@FeignClient(value = "ops-user")
@RequestMapping("user")
public interface UserFacade {
	
	/**
	 * 用户注册
	 * @param userBean
	 * @return
	 */
	@RequestMapping("/register")
	public ResponseInfo register(@RequestBody UserEntity userBean);
	
	/**
	 * 根据用户名查询用户
	 * @param username
	 * @return
	 */
	@RequestMapping("/selectUserByUsername")
	public UserEntity selectUserByUsername(
			@RequestParam("username") String username);
}
