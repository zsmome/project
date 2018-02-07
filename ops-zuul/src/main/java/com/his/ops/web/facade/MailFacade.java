package com.his.ops.web.facade;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.his.ops.dto.ResponseInfo;

@FeignClient(value = "ops-activemq")
@RestController
@RequestMapping("mail")
public interface MailFacade {

	@RequestMapping("/sendActivateMail")
	public ResponseInfo sendRegisterToMail(@RequestParam("username") String username, @RequestParam("activateCode") String activateCode);
	
	@RequestMapping("/sendExceptionMail")
	public void sendExceptionMail(@RequestParam("exceptionMsg") String exceptionMsg);
	
}
