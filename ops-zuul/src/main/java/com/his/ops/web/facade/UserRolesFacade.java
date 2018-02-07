package com.his.ops.web.facade;

import java.util.Set;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.his.ops.entity.UserRolesEntity;

@FeignClient(value = "ops-user")
@RestController
@RequestMapping("userRoles")
public interface UserRolesFacade {

	/**
	 * 根据用户名查询用户拥有的角色
	 * @param username
	 * @return
	 */
	@RequestMapping("/selectRolesByUsername")
	public Set<UserRolesEntity> selectRolesByUsername(
			@RequestParam("username") String username);
	
}
