package com.his.ops.service;

import java.util.Set;

import com.his.ops.entity.UserRolesEntity;


public interface UserRolesService extends BaseService<UserRolesEntity> {
	
	/**
	 * 根据用户名查询用户的角色集合
	 * @param username
	 * @return
	 */
	public Set<UserRolesEntity> selectRolesByUsername(String username);
}
