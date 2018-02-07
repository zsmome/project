package com.his.ops.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.his.ops.entity.UserRolesEntity;
import com.his.ops.mapper.UserRolesEntityMapper;
import com.his.ops.service.UserRolesService;


@Service
public class UserRolesServiceImpl implements UserRolesService {
	
	@Autowired
	private UserRolesEntityMapper mapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(UserRolesEntity record) {
		// TODO Auto-generated method stub
		return mapper.insert(record);
	}

	@Override
	public UserRolesEntity selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public List<UserRolesEntity> selectAll() {
		// TODO Auto-generated method stub
		return mapper.selectAll();
	}

	@Override
	public int updateByPrimaryKey(UserRolesEntity record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public Set<UserRolesEntity> selectRolesByUsername(String username) {
		// TODO Auto-generated method stub
		return mapper.selectRolesByUsername(username);
	}
	
}
