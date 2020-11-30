package com.algaworks.algafood.di.service;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.model.Client;
import com.algaworks.algafood.di.notification.NotifierEmail;

@Component
public class ClientActivationService {
	
	private NotifierEmail notificadorEmail;
	
	public void ativar(Client client) {
		client.activate();
		
		notificadorEmail.notificar(client, "Your registration in the system is active!");
	}

}
