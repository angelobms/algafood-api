package com.algaworks.algafood.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.di.notification.NotifierEmail;
import com.algaworks.algafood.di.service.ClientActivationService;

//@Configuration
public class AlgaFoodConfig {
	
	@Bean
	public NotifierEmail notifierEmail() {
		NotifierEmail notifier = new NotifierEmail("smtp.algamail.com.br");
		notifier.setCapsLock(true);
		
		return notifier;
	}
	
	@Bean
	public  ClientActivationService clientActivationService() {
		return new ClientActivationService(notifierEmail());
	}

}
