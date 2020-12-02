package com.algaworks.algafood.config;

import org.springframework.context.annotation.Bean;

import com.algaworks.algafood.di.service.ClientActivationService;

//@Configuration
public class ServiceConfig {
	
	@Bean(initMethod = "init", destroyMethod = "destroy")
	public ClientActivationService clientActivationService() {
		return new ClientActivationService();
	}
}
