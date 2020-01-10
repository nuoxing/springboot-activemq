package com.work.config;

import javax.jms.ConnectionFactory;
import javax.jms.Session;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

@Configuration
public class ActivemqConfig {
	

	
	/**
	 * 点对点模式的监听器
	 * @param activeMQConnectionFactory
	 * @return
	 */
    @Bean(name = "queueListenerFactory")
    public JmsListenerContainerFactory<?> queueListenerFactory(ConnectionFactory activeMQConnectionFactory){
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setPubSubDomain(false);
        factory.setConnectionFactory(activeMQConnectionFactory);
        
        //手动ack配置
        factory.setSessionTransacted(false);
        factory.setSessionAcknowledgeMode(4);//配置这个没有效果CLIENT_ACKNOWLEDGE = 2
        //手动ack配置
        
        
        //配置消费者的多线程 能同时处理的线程数
        //factory.setConcurrency("7");
        
        return factory;
    }
 
    /**
     * 发布订阅模式的监听器
     * @param activeMQConnectionFactory
     * @return
     */
    @Bean(name = "topicListenerFactory")
    public JmsListenerContainerFactory<DefaultMessageListenerContainer> topicListenerFactory(ConnectionFactory activeMQConnectionFactory){
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setPubSubDomain(true);
        
        //发布订阅模式下的 消息持久化配置
        //持久化 topic 的listenerContainerFactory必须设置 setSubscriptionDurable(true)  setPubSubDomain(true) 并且设置 clientId
        factory.setSubscriptionDurable(true);// Set this to "true" to register a durable subscription,
        factory.setClientId("A");
      //发布订阅模式下的 消息持久化配置
        
        factory.setConnectionFactory(activeMQConnectionFactory);
       
        return factory;
    }
    /**
     * 发布订阅模式的监听器
     * @param activeMQConnectionFactory
     * @return
     */
    @Bean(name = "topicListenerFactory2")
    public JmsListenerContainerFactory<DefaultMessageListenerContainer> topicListenerFactory2(ConnectionFactory activeMQConnectionFactory){
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setPubSubDomain(true);
        
        //发布订阅模式下的 消息持久化配置
        //持久化 topic 的listenerContainerFactory必须设置 setSubscriptionDurable(true)  setPubSubDomain(true) 并且设置 clientId
        factory.setSubscriptionDurable(true);// Set this to "true" to register a durable subscription,
        factory.setClientId("B");
        
        factory.setConnectionFactory(activeMQConnectionFactory);
       
        return factory;
    }


}
