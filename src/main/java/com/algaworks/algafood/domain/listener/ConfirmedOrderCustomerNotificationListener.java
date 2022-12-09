package com.algaworks.algafood.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.algaworks.algafood.domain.event.OrderConfirmedEvent;
import com.algaworks.algafood.domain.model.OrderRestaurant;
import com.algaworks.algafood.domain.service.SendEmailService;
import com.algaworks.algafood.domain.service.SendEmailService.Message;;

@Component
public class ConfirmedOrderCustomerNotificationListener {

	@Autowired
	private SendEmailService sendEmailService;

	@TransactionalEventListener
	public void whenConfirmingOrder(OrderConfirmedEvent event) {
		OrderRestaurant order = event.getOrder();
		
		var message = Message.builder()
				.subject(String.format("%s - Order %s confirmed", order.getRestaurant().getName(), order.getXtid()))
				.templateBody("order-confirmed.html").variable("order", order)
				.recipient(order.getCustomer().getEmail())
				.build();

		sendEmailService.send(message);
	}

}
