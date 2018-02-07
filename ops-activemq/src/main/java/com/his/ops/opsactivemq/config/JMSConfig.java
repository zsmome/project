package com.his.ops.opsactivemq.config;

import javax.jms.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

@Configuration
public class JMSConfig {
	
    // queue出现异常时发送邮件
    @Bean
    public JmsListenerContainerFactory<?> queueSendEeceptionToMail(ConnectionFactory activeMQConnectionFactory) {
    	DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
    	bean.setConnectionFactory(activeMQConnectionFactory);
    	return bean;
    }
    
    
    // topic模式的ListenerContainer
    @Bean
    public JmsListenerContainerFactory<?> topicRelease(ConnectionFactory activeMQConnectionFactory) {
        DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
        bean.setPubSubDomain(true);
        bean.setConnectionFactory(activeMQConnectionFactory);
        return bean;
    }

}
