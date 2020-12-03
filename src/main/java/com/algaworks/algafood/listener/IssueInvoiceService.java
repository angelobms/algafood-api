package com.algaworks.algafood.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.service.ClientActivatedEvent;

@Component
public class IssueInvoiceService {

	@EventListener
	public void clientActivatedListener(ClientActivatedEvent event) {
		System.out.println("Issuing invoice to the customer " + event.getClient().getName());
	}

}
