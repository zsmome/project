package com.his.ops.opsactivemq.Rest;

import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.his.ops.opsactivemq.dto.ResponseInfo;
import com.his.ops.opsactivemq.mq.JmsSendExceptionMail;
import com.his.ops.opsactivemq.service.MailService;
import com.his.ops.opsactivemq.util.MQCommand;

@RestController
@RequestMapping("mail")
public class MailRest {
	
	private static final Logger LOG = LoggerFactory.getLogger(MailRest.class);
	
	@Autowired  
    private MailService mailService;  
      
    @Autowired  
    VelocityEngine velocityEngine; 
	
	@Value("${eureka.instance.hostname}")
	String hostname;
	
	@Value("${server.port}")
	String port;
	
	String zuulURL = "192.168.183.1";
	
	/**
	 * 发送激活邮件
	 * @param username
	 */
	@RequestMapping(value="/sendActivateMail", method={RequestMethod.GET, RequestMethod.POST})
	public ResponseInfo sendRegisterToMail(@RequestParam("username") String username, @RequestParam("activateCode") String activateCode) {
		Map<String, Object> model = new HashMap<>();
		String url = "http://"+hostname+"/activate?"
				+ "activateCode=" + activateCode;
		model.put("activateURL", url);
		
		String content = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "vm/activate.vm", "UTF-8", model);
        
        try {
			mailService.sendHtmlMail(username, "激活康复中心帐号", content);
			LOG.info("成功发送邮箱,激活码："+activateCode);
			return new ResponseInfo(
					ResponseInfo.STATUS_SUCCESS,
					"成功发送邮箱，请前往邮箱激活"
					);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.info("发送邮件失败，请检查邮件地址");
			return new ResponseInfo(
					ResponseInfo.STATUS_FAIL,
					"发送邮件失败，请检查邮件地址"
					);
		}
	}
	
	@Autowired
	private JmsSendExceptionMail JmsSendExceptionMail;
	
	/**
	 * 发送异常邮件
	 * @param exceptionMsg
	 */
	@RequestMapping(value="/sendExceptionMail", method={RequestMethod.GET, RequestMethod.POST})
	public ResponseInfo sendExceptionMail(@RequestParam("exceptionMsg") String exceptionMsg) {
		JmsSendExceptionMail.sendMessage(MQCommand.QUEUE_SEND_EXCEPTION_TO_MAIL, exceptionMsg);
		LOG.info("异常消息邮件成功发送");
		return new ResponseInfo(
				ResponseInfo.STATUS_SUCCESS,
				"异常消息邮件成功发送"
				);
	}
}
