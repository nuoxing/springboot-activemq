package com.work.service;

//消息生产者

import javax.jms.Destination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
 
@Service("producer")
public class Producer {
	@Autowired // 也可以注入JmsTemplate，JmsMessagingTemplate对JmsTemplate进行了封装
	private JmsMessagingTemplate jmsTemplate;
	// 发送消息，destination是发送到的队列，message是待发送的消息
	public void sendMessage(Destination destination, final String message){
		jmsTemplate.getJmsTemplate().setExplicitQosEnabled(true);//是否启动  这几个参数配置 deliveryMode, priority, and timeToLive
		jmsTemplate.getJmsTemplate().setTimeToLive(2000);//消息过期时间设置
		jmsTemplate.convertAndSend(destination, message);
	}
}

