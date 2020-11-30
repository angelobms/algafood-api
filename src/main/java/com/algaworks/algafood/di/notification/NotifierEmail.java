package com.algaworks.algafood.di.notification;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.model.Client;

@Component
public class NotifierEmail implements Notifier {
	
	public NotifierEmail() {
		System.out.println("NotifierEmail");
	}

	@Override
	public void notify(Client client, String message) {
		System.out.printf("Notifying %s through email %s: %s\n", 
				client.getName(), client.getEmail(), message);
	}
}
