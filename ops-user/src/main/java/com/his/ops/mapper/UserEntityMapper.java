package com.his.ops.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.his.ops.entity.UserEntity;
/**
 * 用户
 * @author Administrator
 *
 */
@Mapper
@Repository
public interface UserEntityMapper extends BaseMapper<UserEntity> {
	
	/**
	 * 根据用户名查询用户
	 * @param username 用户名
	 * @return
	 */
	public UserEntity selectUserByUsername(String username);
}