package com.his.ops.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.his.ops.entity.UserEntity;
import com.his.ops.entity.UserInfoEntity;
import com.his.ops.mapper.UserEntityMapper;
import com.his.ops.service.UserInfoService;
import com.his.ops.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserEntityMapper userBeanMapper;
	
	@Autowired
	private UserInfoService userInfoService;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return userBeanMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(UserEntity record) {
		// TODO Auto-generated method stub
		//用户信息表
		String imgPath = "http://192.168.183.130:9999/group1/M00/00/00/wKi3glp2WfKANP-YAAAasmRqhgk367.jpg";
		String username = record.getUsername();
		
		UserInfoEntity userInfoBean = new UserInfoEntity();
		userInfoBean.setUsername(username);
		userInfoBean.setHeadPortraits(imgPath);
		
		userInfoService.insert(userInfoBean);
		return userBeanMapper.insert(record);
	}

	@Override
	public UserEntity selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return userBeanMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<UserEntity> selectAll() {
		// TODO Auto-generated method stub
		return userBeanMapper.selectAll();
	}

	@Override
	public int updateByPrimaryKey(UserEntity record) {
		// TODO Auto-generated method stub
		return userBeanMapper.updateByPrimaryKey(record);
	}

	@Override
	public UserEntity selectUserByUsername(String username) {
		// TODO Auto-generated method stub
		return userBeanMapper.selectUserByUsername(username);
	}
	
}
