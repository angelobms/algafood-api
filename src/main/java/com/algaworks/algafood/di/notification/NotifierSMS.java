package com.algaworks.algafood.di.notification;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.model.Client;

@TypeNotification(UrgencyLevel.URGENT)
@Component
public class NotifierSMS implements Notifier {

	@Override
	public void notify(Client client, String message) {
		System.out.printf("Notifying SMS %s through phone %s: %s\n", 
				client.getName(), client.getPhone(), message);
	}

}
