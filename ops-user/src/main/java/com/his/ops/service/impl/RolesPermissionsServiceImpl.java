package com.his.ops.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.his.ops.entity.RolesPermissionsEntity;
import com.his.ops.mapper.RolesPermissionsEntityMapper;
import com.his.ops.service.RolesPermissionsService;


@Service
public class RolesPermissionsServiceImpl implements RolesPermissionsService {
	
	@Autowired
	private RolesPermissionsEntityMapper mapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(RolesPermissionsEntity record) {
		// TODO Auto-generated method stub
		return mapper.insert(record);
	}

	@Override
	public RolesPermissionsEntity selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public List<RolesPermissionsEntity> selectAll() {
		// TODO Auto-generated method stub
		return mapper.selectAll();
	}

	@Override
	public int updateByPrimaryKey(RolesPermissionsEntity record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public Set<RolesPermissionsEntity> selectRolesPermissionsByRoleName(String roleName) {
		// TODO Auto-generated method stub
		return mapper.selectRolesPermissionsByRoleName(roleName);
	}
	
}
