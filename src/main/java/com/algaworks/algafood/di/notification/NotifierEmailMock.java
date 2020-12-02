package com.algaworks.algafood.di.notification;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.model.Client;

@Profile("dev")
@TypeNotification(UrgencyLevel.NORMAL)
@Component
public class NotifierEmailMock implements Notifier {
	
	@Override
	public void notify(Client client, String message) {
		System.out.printf("MOCK: Notifying would be sent to %s through email %s: %s\n", 
				client.getName(), client.getEmail(), message);
	}

}
