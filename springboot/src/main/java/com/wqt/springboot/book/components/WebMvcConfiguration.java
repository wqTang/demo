package com.wqt.springboot.book.components;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.wqt.springboot.book.interceptors.LoginInterceptor;

/**
 * Remember add @Configuration annotation.
 * 
 * @author iuShu
 * @date Mar 30, 2018 8:48:38 PM
 */
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginInterceptor());
	}

}
