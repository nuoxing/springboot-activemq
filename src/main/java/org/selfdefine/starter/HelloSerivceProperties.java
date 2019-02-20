package org.selfdefine.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("helloservice")
public class HelloSerivceProperties {

	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
