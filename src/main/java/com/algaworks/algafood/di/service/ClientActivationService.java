package com.algaworks.algafood.di.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.algaworks.algafood.di.model.Client;
import com.algaworks.algafood.di.notification.Notifier;
import com.algaworks.algafood.di.notification.TypeNotification;
import com.algaworks.algafood.di.notification.UrgencyLevel;
	
//@Component
public class ClientActivationService {
	
	@TypeNotification(UrgencyLevel.URGENT)
	@Autowired
	private Notifier notifier;
	
//	@PostConstruct
	public void init() {
		System.out.println("INIT " + notifier);
	}
	
//	@PreDestroy
	public void destroy() {
		System.out.println("DESTROY");
	}

	public void activate(Client client) {
		client.activate();
		
		notifier.notify(client, "Your registration in the system is active!");
	}

}
