package com.algaworks.algafood.di.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.model.Client;
import com.algaworks.algafood.di.notification.Notifier;
	
@Component
public class ClientActivationService {
	
	@Autowired
	private Notifier notifier;

	public void activate(Client client) {
		client.activate();
		
		notifier.notify(client, "Your registration in the system is active!");
	}

}
