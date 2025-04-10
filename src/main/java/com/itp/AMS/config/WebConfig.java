package com.itp.AMS.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**")
		.addResourceLocations("classpath:/static/images/");

		registry.addResourceHandler("/css/**")
		.addResourceLocations("classpath:/static/css/");
		
		registry.addResourceHandler("/javascript/**")
		.addResourceLocations("classpath:/static/javascript/");
		
	}
}
