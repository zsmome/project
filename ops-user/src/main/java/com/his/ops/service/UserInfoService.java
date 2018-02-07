package com.his.ops.service;

import com.his.ops.entity.UserInfoEntity;

public interface UserInfoService extends BaseService<UserInfoEntity> {
	/**
	 * 根据用户名修改数据
	 * @param username
	 * @return
	 */
	public int updateUserInfoByUsername(UserInfoEntity record);
	
	/**
	 * 根据用户名查询数据
	 * @param username
	 * @return
	 */
	public UserInfoEntity selectUserInfoByUsername(String username);
}
