package com.his.ops.web.rest;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.his.ops.entity.RolesPermissionsEntity;
import com.his.ops.service.RolesPermissionsService;

@RestController
@RequestMapping("rolesPermissions")
public class RolesPermissionsRest {

	//打印日志信息
	private final static Logger LOG = LoggerFactory.getLogger(RolesPermissionsRest.class);
	
	@Autowired
	private RolesPermissionsService rolesPermissionsService;
	
	/**
	 * 根据角色名查找角色拥有的所有权限
	 * @param roleName
	 * @return
	 */
	@RequestMapping("/selectRolesPermissionsByRoleName")
	public Set<RolesPermissionsEntity> selectRolesPermissionsByRoleName(String roleName) {
		Set<RolesPermissionsEntity> set = rolesPermissionsService.selectRolesPermissionsByRoleName(roleName);
		LOG.info("根据角色名查找角色拥有的所有权限-->" +roleName+"-->"+set);
		return set;
	}
	
}
