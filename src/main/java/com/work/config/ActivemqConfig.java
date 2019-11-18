package com.work.config;

import javax.jms.ConnectionFactory;

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
