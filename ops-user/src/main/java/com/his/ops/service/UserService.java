package com.his.ops.service;

import com.his.ops.entity.UserEntity;

public interface UserService extends BaseService<UserEntity> {
	
	/**
	 * 根据用户名查询用户
	 * @param username
	 * @return
	 */
	public UserEntity selectUserByUsername(String username);
}
