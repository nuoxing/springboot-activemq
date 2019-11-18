package com.work.service;


import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
 
//@Component
public class Consumer3 {
        // 使用JmsListener配置消费者监听的topic，其中text是接收到的消息 发布 订阅模式
	@JmsListener(destination = "mytest.topic",containerFactory="topicListenerFactory")
	public void receiveQueue(String text) {
		System.out.println("Consumer3收到的报文为:"+text);
	}

}