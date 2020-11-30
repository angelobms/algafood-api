package com.algaworks.algafood.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.di.notification.NotifierEmail;

@Configuration
public class NotificationConfig {
	
	@Bean
	public NotifierEmail notifierEmail() {
		NotifierEmail notifier = new NotifierEmail("smtp.algamail.com.br");
		notifier.setCapsLock(true);
		
		return notifier;
	}

}
