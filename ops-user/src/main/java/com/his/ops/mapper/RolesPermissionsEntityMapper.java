package com.his.ops.mapper;


import java.util.Set;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.his.ops.entity.RolesPermissionsEntity;
/**
 * 角色拥有的权限
 * @author Administrator
 *
 */
@Mapper
@Repository
public interface RolesPermissionsEntityMapper extends BaseMapper<RolesPermissionsEntity> {
	/**
	 * 根据角色名查询角色拥有的所有权限
	 * @param username
	 * @return
	 */
	public Set<RolesPermissionsEntity> selectRolesPermissionsByRoleName(String roleName);
}