package com.his.ops.mapper;


import java.util.Set;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.his.ops.entity.UserRolesEntity;
/**
 * 用户所拥有的角色
 * @author Administrator
 *
 */
@Mapper
@Repository
public interface UserRolesEntityMapper extends BaseMapper<UserRolesEntity> {
	/**
	 * 根据用户名查询用户所拥有的角色
	 * @param username
	 */
	public Set<UserRolesEntity> selectRolesByUsername(String username);
}