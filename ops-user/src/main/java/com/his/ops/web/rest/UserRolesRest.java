package com.his.ops.web.rest;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.his.ops.entity.UserRolesEntity;
import com.his.ops.service.UserRolesService;

@RestController
@RequestMapping("userRoles")
public class UserRolesRest {
	
	//打印日志信息
	private final static Logger LOG = LoggerFactory.getLogger(UserRolesRest.class);
	
	@Autowired
	private UserRolesService userRolesService;

	@RequestMapping("/selectRolesByUsername")
	public Set<UserRolesEntity> selectRolesByUsername(String username) {
		Set<UserRolesEntity> set = userRolesService.selectRolesByUsername(username);
		LOG.info("根据用户名查找用户的所有角色-->"+set);
		return set;
	}
	
}
