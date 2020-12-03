package com.algaworks.algafood.di.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.model.Client;
	
@Component
public class ClientActivationService {
	
//	@TypeNotification(UrgencyLevel.URGENT)
//	@Autowired
//	private Notifier notifier;
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
//	@PostConstruct
//	public void init() {
//		System.out.println("INIT " + notifier);
//	}
	
//	@PreDestroy
//	public void destroy() {
//		System.out.println("DESTROY");
//	}

	public void activate(Client client) {
		client.activate();
		
		//notifier.notify(client, "Your registration in the system is active!");
		eventPublisher.publishEvent(new ClientActivatedEvent(client));
	}

}
