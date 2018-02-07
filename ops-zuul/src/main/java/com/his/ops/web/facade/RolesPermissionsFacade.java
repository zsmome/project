package com.his.ops.web.facade;

import java.util.Set;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.his.ops.entity.RolesPermissionsEntity;

@FeignClient(value = "ops-user")
@RestController
@RequestMapping("rolesPermissions")
public interface RolesPermissionsFacade {

	/**
	 * 根据角色名查询角色的所有权限
	 * @param roleName
	 * @return
	 */
	@RequestMapping("/selectRolesPermissionsByRoleName")
	public Set<RolesPermissionsEntity> selectRolesPermissionsByRoleName(
			@RequestParam("roleName") String roleName);
	
}
