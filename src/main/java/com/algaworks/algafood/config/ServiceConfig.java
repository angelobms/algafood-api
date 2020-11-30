package com.algaworks.algafood.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.di.notification.Notifier;
import com.algaworks.algafood.di.service.ClientActivationService;

@Configuration
public class ServiceConfig {
	
	@Bean
	public  ClientActivationService clientActivationService(Notifier notifier) {
		return new ClientActivationService(notifier);
	}

}
