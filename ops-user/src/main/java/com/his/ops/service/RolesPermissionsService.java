package com.his.ops.service;


import java.util.Set;

import com.his.ops.entity.RolesPermissionsEntity;


public interface RolesPermissionsService extends BaseService<RolesPermissionsEntity> {
	/**
	 * 根据角色名查询角色的权限
	 * @param roleName 角色名
	 * @return
	 */
	public Set<RolesPermissionsEntity> selectRolesPermissionsByRoleName(String roleName);
}
