package com.algaworks.algafood.di.service;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.model.Client;
import com.algaworks.algafood.di.notification.Notifier;

@Component
public class ClientActivationService {
	
	private Notifier notificador;
	
	public ClientActivationService(Notifier notificador) {
		this.notificador = notificador;
		
		System.out.println("ClientActivationService: " + notificador);
	}

	public void activate(Client client) {
		client.activate();
		
		notificador.notify(client, "Your registration in the system is active!");
	}

}
