package com.currency.rest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyAppWebConfig extends WebMvcConfigurerAdapter {
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// I tried these many combinations separately.
		System.out.println("@@@@@@@@@@@@@@@@@@@@MyAppWebConfig@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		ResourceHandlerRegistration resourceRegistration = registry.addResourceHandler("resources/**");
		resourceRegistration.addResourceLocations("/resources/**");
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		registry.addResourceHandler("/css/**").addResourceLocations("/css/**");
		registry.addResourceHandler("/img/**").addResourceLocations("/img/**");
		registry.addResourceHandler("/js/**").addResourceLocations("/js/**");
		// registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/resources/");
		// do the classpath works with the directory under webapp?
	}

}
