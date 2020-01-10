package com.work.controller;

import javax.jms.Destination;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.work.service.Producer;
 
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootJmsApplicationTests {
	
	@Autowired
	private Producer producer;
	
	/**
	 * 发送到队列上，点对点或队列模型
	 * 一条消息只有一个消费者
	 * @throws InterruptedException
	 */
	@Test
	public void contextLoads() throws InterruptedException {
	/*	Destination destination = new ActiveMQQueue("mytest.queue");
		for(int i=0; i<10; i++){
			producer.sendMessage(destination, "myname is chhliu!!!");
		}*/
		Thread.sleep(100000);
	}
	
	/**
	 * 发布订阅模式
	 * @throws InterruptedException
	 */
	@Test
	public void test() throws InterruptedException {
		Destination destination = new ActiveMQTopic("mytest.topic");
		for(int i=0; i<2; i++){
			producer.sendMessage(destination, "topic is chhliu!!!");
		}
		Thread.sleep(100000);
	}
 
	
	/**
	 * 虚拟topic测试
	 * @throws InterruptedException
	 */
	@Test
	public void test2() throws InterruptedException {
		Destination destination = new ActiveMQTopic("VirtualTopic.topic");
		for(int i=0; i<2; i++){
			producer.sendMessage(destination, "VirtualTopic is chhliu!!!");
		}
		Thread.sleep(100000);
	}
 
}
