package com.work.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * springMVC 配置文件
 * 在此可添加拦截器
 * 设置跨域请求
 * 
 * @author ljj
 *
 */
@Configuration
public class SpringMVCConfig extends WebMvcConfigurerAdapter {

	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 例如增加用户是否登录的拦截器
		
    }
	
	@Override
    public void addCorsMappings(CorsRegistry registry) {
		/**
		 * 全局跨域请求设置 允许那个地方访问
		 * 
		 * 也可以在单独的controller的方法上面加上
		 * @CrossOrigin(origins = "http://localhost:3000")
		 * 实现该方法可以被跨域访问
		 * 
		 */
        //registry.addMapping("/**").allowedOrigins("http://localhost:3000");
    }
}
