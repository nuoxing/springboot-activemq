package com.work.service;


import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
 
@Component
public class Consumer6 {
        // 使用JmsListener配置消费者监听的topic，其中text是接收到的消息 虚拟topic
	@JmsListener(destination = "Consumer.6.VirtualTopic.topic",containerFactory="queueListenerFactory")
	public void receiveQueue(String text) {
		System.out.println("Consumer6收到的报文为:"+text);
	}

}