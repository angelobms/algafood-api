package com.algaworks.algafood.di.notification;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.model.Client;

@Profile("prod")
@TypeNotification(UrgencyLevel.NORMAL)
@Component
public class NotifierEmail implements Notifier {

	@Override
	public void notify(Client client, String message) {
		System.out.printf("Notifying %s through email %s: %s\n", 
				client.getName(), client.getEmail(), message);
	}

}
