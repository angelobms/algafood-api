package com.algaworks.algafood.di.service;

import com.algaworks.algafood.di.model.Client;
import com.algaworks.algafood.di.notification.Notifier;

public class ClientActivationService {
	
	private Notifier notifier;
	
	public ClientActivationService(Notifier notifier) {
		this.notifier = notifier;
		
		System.out.println("ClientActivationService: " + notifier);
	}

	public void activate(Client client) {
		client.activate();
		
		notifier.notify(client, "Your registration in the system is active!");
	}

}
