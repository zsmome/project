package com.his.ops.opsactivemq.util;

/**
 * MQ指令
 * @author Administrator
 *
 */
public interface MQCommand {
	
	/**
	 * 注册时发送邮件来激活
	 */
	public static final String QUEUE_SEND_EXCEPTION_TO_MAIL = "queueSendEeceptionToMail";
	
}
