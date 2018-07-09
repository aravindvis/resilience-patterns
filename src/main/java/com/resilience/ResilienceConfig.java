package com.resilience;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.resilience.manager.user.UserManager;
import com.resilience.manager.user.impl.UserManagerImpl;

@Configuration
public class ResilienceConfig {

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	@Bean
	public UserManager getUserManager() {
		return new UserManagerImpl();
	}
}
