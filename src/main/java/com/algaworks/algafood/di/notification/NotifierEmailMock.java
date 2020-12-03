package com.algaworks.algafood.di.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.model.Client;

@Profile("dev")
@TypeNotification(UrgencyLevel.NORMAL)
@Component
public class NotifierEmailMock implements Notifier {
	
//	@Value("${notifier.email.host-server}")
//	private String host;
//	
//	@Value("${notifier.email.port-server}")
//	private Integer port;
	
	@Autowired
	private NotifierProperties properties;
	
	@Override
	public void notify(Client client, String message) {
		System.out.println("Host: " + properties.getHostServer());
		System.out.println("Port: " + properties.getPortServer());
		
		System.out.printf("MOCK: Notifying would be sent to %s through email %s: %s\n", 
				client.getName(), client.getEmail(), message);
	}

}
