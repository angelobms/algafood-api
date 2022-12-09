package com.algaworks.algafood.domain.event;

import com.algaworks.algafood.domain.model.OrderRestaurant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderConfirmedEvent {
	
	private OrderRestaurant order;
}
