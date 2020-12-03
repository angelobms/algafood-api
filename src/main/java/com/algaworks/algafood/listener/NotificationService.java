package com.algaworks.algafood.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.notification.Notifier;
import com.algaworks.algafood.di.notification.TypeNotification;
import com.algaworks.algafood.di.notification.UrgencyLevel;
import com.algaworks.algafood.di.service.ClientActivatedEvent;

@Component
public class NotificationService {
	
	@TypeNotification(UrgencyLevel.NORMAL)
	@Autowired
	private Notifier notifier;
	
	@EventListener
	public void clientActivatedListener(ClientActivatedEvent event) {
		notifier.notify(event.getClient(), "Your registration in the system is active!");
	}

}
