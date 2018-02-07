package com.his.ops.web.contorller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.his.ops.dto.ResponseInfo;
import com.his.ops.entity.UserEntity;
import com.his.ops.service.RedisService;
import com.his.ops.utils.ErrorMessageHelper;
import com.his.ops.utils.PasswordUtils;
import com.his.ops.web.facade.MailFacade;
import com.his.ops.web.facade.UserFacade;

@Controller
public class LoginAndRegisterContorller {
	
	//打印日志信息
	private final static Logger LOG = LoggerFactory.getLogger(LoginAndRegisterContorller.class);
	
	/**
	 * 用户层操作
	 */
	@Autowired
	private UserFacade userFacade;
	
	@Autowired
	private MailFacade mailFacade;
	
	@Value("${server.port}")
	String port;
	
	@RequestMapping("/activateInvalid")
	public String activateInvalid() {
		return "activateInvalid";
	}
	
	/**
	 * 登录界面-已经登录直接跳转
	 */
	@RequestMapping(value="/login", method={RequestMethod.GET})
	public String loginView(HttpServletRequest request) {
		LOG.info("当前访问的端口号："+port);
		Subject subject = SecurityUtils.getSubject();
		//已经登录到首页
		if(subject.isAuthenticated() || subject.isRemembered()) {
			
			mailFacade.sendExceptionMail("尝试强制登录系统被拒绝！");
			
			LOG.info("进入登录页面 重定向到 index.jsp");
			return "redirect:/admin/index";
		}
		LOG.info("访问登录页面");
		return "login";
	}
	
	/**
	 * 注册界面
	 */
	@RequestMapping(value="/register", method={RequestMethod.GET})
	public String registerView() {
		LOG.info("访问注册页面");
		return "register";
	}
	
	/**
	 * 登录时响应
	 * @param bean
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/loginAction", method={RequestMethod.POST})
	@ResponseBody
	public Object loginDo(@RequestParam("username")String username, 
			@RequestParam("password")String password, 
			@RequestParam("rememberMe")boolean rememberMe) {
		LOG.info(username);
		LOG.info(password);
		LOG.info(""+rememberMe);
		Map<String, String> map = new HashMap<>();
						
		//加密-与数据库做匹配
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		token.setRememberMe(rememberMe);
		Subject subject = SecurityUtils.getSubject();	
		Session session = subject.getSession(true);
		session.setAttribute("loginCount", null);
		session.setAttribute("message", null);
		try {
			subject.login(token);
		} catch (Exception e) {
			// TODO: handle exception
			LOG.info("登录失败");
			map.put("failed", "用户名或密码不正确！");
			String loginCount = (String) session.getAttribute("loginCount");
			if(loginCount != null) {
				map.put("loginCount", loginCount);
			}
			return map;
		}
		map.put("success", "登录成功!");
		LOG.info("登录成功");
		//发送队列登录消息
		return map;
	}
	
	public boolean serviceStop(String username) {
		LOG.info("服务器停止了"+username);
		return false;
	}
	
	/**
	 * 用户名是否存在
	 */
	@RequestMapping(value="/isExistUsername")
	@ResponseBody
	public boolean isExistUsername(String username) {
		
		UserEntity user = userFacade.selectUserByUsername(username);
		Boolean isExist = false;
		if(user != null) {
			LOG.info("用户名已经存在:"+username);
			isExist = true;
		}
		return isExist;
	}
	
	@Autowired
	RedisService redisService;
	
	/**
	 * 发送激活邮件
	 * @return
	 */
	@RequestMapping("/registerAction")
	@ResponseBody
	public Object activate(@Valid @RequestBody UserEntity userEntity, BindingResult result) {
		
		Map<String, List<String>> errorMessageMap;
		errorMessageMap = ErrorMessageHelper.errorMessageResult(result);
		
		//有错误信息，返回出去
		if(errorMessageMap != null) {
			LOG.info("注册用户有验证错误信息:"+errorMessageMap);
			return errorMessageMap;
		}
		
		//加密
		PasswordUtils.encryptPassword(userEntity);
		
		Random random = new Random();
		String activateCode;
		do {
			activateCode = random.nextLong() + "";
			
		} while(redisService.exists(activateCode+""));
		
		redisService.expireTimeSave(activateCode, userEntity, 120L);
		
		LOG.info(userEntity.getUsername()+"激活码："+activateCode);
		
		return mailFacade.sendRegisterToMail(userEntity.getUsername(), activateCode);
	}
	
	/**
	 * 注册时响应
	 * @param bean
	 * @return
	 */
	@RequestMapping(value="/activate")
	public String registerDo(String activateCode) {
		UserEntity userCache = (UserEntity) redisService.get(activateCode);
		//激活码失效
		if(userCache == null) {
			return "activateInvalid";
		}
		LOG.info(userCache.toString());
		//清除缓存
		redisService.removeKey(activateCode);
		ResponseInfo responseInfo =  userFacade.register(userCache);
		if(ResponseInfo.STATUS_SUCCESS.equals(responseInfo.getResponseStatus())) {
			return "login";
		} else {
			//注册页
			return "register";
		}
	}
}
