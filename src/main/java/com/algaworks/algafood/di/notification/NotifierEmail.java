package com.algaworks.algafood.di.notification;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.model.Client;

@Component
public class NotifierEmail {

	public void notificar(Client client, String message) {
		System.out.printf("Notifying %s through email %s: %s\n", 
				client.getName(), client.getEmail(), message);
	}
}
