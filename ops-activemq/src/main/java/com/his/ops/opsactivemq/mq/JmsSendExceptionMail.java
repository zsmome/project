package com.his.ops.opsactivemq.mq;


import java.util.HashMap;
import java.util.Map;

import javax.jms.Destination;
import javax.mail.MessagingException;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.his.ops.opsactivemq.service.MailService;
import com.his.ops.opsactivemq.util.MQCommand;
import com.his.ops.opsactivemq.util.MailVMPath;

/**
 * 发送异常邮件
 * @author Administrator
 *
 */
@Component
public class JmsSendExceptionMail {
	
	private final static Logger logger = LoggerFactory.getLogger(JmsSendExceptionMail.class);

	public static final String to = "718932505@qq.com";
	
	@Autowired
	MailService mailService;
	
	@Autowired  
    VelocityEngine velocityEngine; 
	
    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendMessage(String queueName,String exceptionMsg) {
    	Destination destination = new ActiveMQQueue(queueName);
        this.jmsTemplate.convertAndSend(destination,exceptionMsg);
    }
    
    @JmsListener(destination = MQCommand.QUEUE_SEND_EXCEPTION_TO_MAIL,containerFactory = MQCommand.QUEUE_SEND_EXCEPTION_TO_MAIL)
    public void onRegisterMessage(String exceptionMsg) throws MessagingException {
    	Map<String, Object> model = new HashMap<>();
    	model.put("exceptionMsg", exceptionMsg);
    	String content = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, MailVMPath.EXCEPTION, "UTF-8", model);  
        
        mailService.sendHtmlMail(to, "服务器发生异常错误", content);
    	//发送邮件
    }
    
}
