package com.his.ops.opsfdfs.facade;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.his.ops.opsfdfs.entity.UserInfoEntity;

@FeignClient(value = "ops-user")
@RestController
@RequestMapping("userInfo")
public interface UserInfoFacade {

	/**
	 * 根据 用户名修改用户信息
	 * @param username
	 * @param headPortraits
	 * @return
	 */
	@RequestMapping("/updateUserInfoByUsername")
	public int updateUserInfoByUsername(
			@RequestParam("username") String username,
			@RequestParam("headPortraits") String headPortraits
			);
	
	/**
	 * 根据用户名查询用户信息
	 * @param username
	 * @return
	 */
	@RequestMapping("/selectUserInfoByUsername")
	public UserInfoEntity selectUserInfoByUsername(
			@RequestParam("username") String username);
	
}
