package com.his.ops.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.his.ops.entity.UserInfoEntity;

@Mapper
@Repository
public interface UserInfoEntityMapper extends BaseMapper<UserInfoEntity> {
	
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