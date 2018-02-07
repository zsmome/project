package com.his.ops.web.rest;


import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.his.ops.dto.ResponseInfo;
import com.his.ops.entity.UserEntity;
import com.his.ops.service.UserService;
import com.his.ops.utils.ErrorMessageHelper;

@RestController
@RequestMapping("user")
public class LoginAndRegisterRest {
	
	//打印日志信息
	private final static Logger LOG = LoggerFactory.getLogger(LoginAndRegisterRest.class);
	
	/**
	 * 用户层操作
	 */
	@Autowired
	private UserService userService;
	
	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping("/login")
	public ResponseInfo login(String username, String password) {
		ResponseInfo responseInfo = null;
		
		UserEntity userBean = userService.selectUserByUsername(username);
		//判断用户名密码是否匹配
		if(userBean != null && userBean.getPassword().equalsIgnoreCase(password)) {
			//登录成功
			responseInfo = new ResponseInfo(
					ResponseInfo.STATUS_SUCCESS, 
					"登录成功");
			LOG.info(username+"-->登录成功");
		} else {
			responseInfo = new ResponseInfo(
					ResponseInfo.STATUS_FAIL, 
					"登录失败");
			LOG.info(username+"-->登录失败");
		}
		return responseInfo;
	}
	
	/**
	 * 用户注册
	 * @param userBean
	 * @return
	 */
	@RequestMapping("/register")
	public ResponseInfo register(@Valid @RequestBody UserEntity userBean, BindingResult result) {
		
		LOG.info(userBean.toString());
		ResponseInfo responseInfo = null;
		
		//字段信息
		Map<String, List<String>> messageResult = ErrorMessageHelper.errorMessageResult(result);
		//有字段不符合要求信息
		if(messageResult != null) {
			responseInfo = new ResponseInfo(
					ResponseInfo.STATUS_ERROR,
					messageResult);
			LOG.info("字段不符合要求-->"+messageResult);
			return responseInfo;
		}
		
		
		//是否已经存在
		ResponseInfo existResponseInfo = isExistusername(userBean.getUsername());
		//不存在,可以注册
		if(!existResponseInfo.getResponseStatus()
				.equals(ResponseInfo.STATUS_SUCCESS)) {
			responseInfo = new ResponseInfo(
					ResponseInfo.STATUS_SUCCESS,
					"注册成功");
			userService.insert(userBean);
			LOG.info("注册成功-->"+userBean.getUsername());
		//存在，不可以注册
		} else {
			responseInfo = new ResponseInfo(
					ResponseInfo.STATUS_FAIL,
					"注册失败");
			LOG.info("注册失败-->"+userBean.getUsername());
		}
		return responseInfo;
	}
	
	/**
	 * 用户名是否存在
	 * @param username
	 * @return
	 */
	@RequestMapping("/isExistUsername")
	public ResponseInfo isExistusername(String username) {
		ResponseInfo responseInfo = null;
		
		UserEntity userBean = userService.selectUserByUsername(username);
		//存在
		if(userBean != null) {
			responseInfo = new ResponseInfo(
					ResponseInfo.STATUS_SUCCESS,
					"用户名存在");
			LOG.info("用户名存在-->"+username);
		//不存在
		} else {
			responseInfo = new ResponseInfo(
					ResponseInfo.STATUS_FAIL,
					"用户名不存在");
			LOG.info("用户名不存在-->"+username);
		}
		return responseInfo;
	}
}
