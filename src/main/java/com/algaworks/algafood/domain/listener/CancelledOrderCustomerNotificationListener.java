package com.algaworks.algafood.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.algaworks.algafood.domain.event.OrderCancelledEvent;
import com.algaworks.algafood.domain.model.OrderRestaurant;
import com.algaworks.algafood.domain.service.SendEmailService;
import com.algaworks.algafood.domain.service.SendEmailService.Message;;

@Component
public class CancelledOrderCustomerNotificationListener {

	@Autowired
	private SendEmailService sendEmailService;

	@TransactionalEventListener
	public void whenCancelingOrder(OrderCancelledEvent event) {
		OrderRestaurant order = event.getOrder();
		
		var message = Message.builder()
				.subject(String.format("%s - Order %s cancelled", order.getRestaurant().getName(), order.getXtid()))
				.templateBody("order-cancelled.html").variable("order", order)
				.recipient(order.getCustomer().getEmail())
				.build();

		sendEmailService.send(message);
	}

}
