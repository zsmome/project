package com.his.ops.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.his.ops.entity.UserInfoEntity;
import com.his.ops.mapper.UserInfoEntityMapper;
import com.his.ops.service.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	UserInfoEntityMapper mapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(UserInfoEntity record) {
		// TODO Auto-generated method stub
		return mapper.insert(record);
	}

	@Override
	public UserInfoEntity selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public List<UserInfoEntity> selectAll() {
		// TODO Auto-generated method stub
		return mapper.selectAll();
	}

	@Override
	public int updateByPrimaryKey(UserInfoEntity record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateUserInfoByUsername(UserInfoEntity record) {
		// TODO Auto-generated method stub
		return mapper.updateUserInfoByUsername(record);
	}

	@Override
	public UserInfoEntity selectUserInfoByUsername(String username) {
		// TODO Auto-generated method stub
		return mapper.selectUserInfoByUsername(username);
	}

}
