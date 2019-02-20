package org.selfdefine.starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //这个一个配置类
@ConditionalOnClass(HelloService.class)//条件注解 存在这个类 才解析下面的属性配置文件
@EnableConfigurationProperties(HelloSerivceProperties.class)
public class HelloServiceAutoConfigure {

	
	@Autowired
	private HelloSerivceProperties helloSerivceProperties;
	
	public HelloServiceAutoConfigure(){
		System.out.println("HelloServiceAutoConfigure实例化了。。。。。");
	}
	
	
	@Bean
	@ConditionalOnClass(HelloService.class)
	@ConditionalOnMissingBean(HelloService.class)
	public HelloService instanceHelloService(){
		return new HelloService(helloSerivceProperties.getName());
	}
	
}
