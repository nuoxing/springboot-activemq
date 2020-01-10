package com.work.service;


import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
 
@Component
public class Consumer {
    // 使用JmsListener配置消费者监听的队列，其中text是接收到的消息
	@JmsListener(destination = "mytest.queue",containerFactory="queueListenerFactory",concurrency="5")
	public void receiveQueue(Message message) {
		try {
			System.out.println("接收线程"+Thread.currentThread().getName()+"Consumer收到的报文为:"+ ((TextMessage)message).getText());
		
			//手动ack
			message.acknowledge();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}