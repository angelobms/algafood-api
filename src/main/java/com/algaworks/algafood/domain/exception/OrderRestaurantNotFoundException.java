package com.algaworks.algafood.domain.exception;

public class OrderRestaurantNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;
	
	public OrderRestaurantNotFoundException(String xtid) {
		super(String.format("There is no order with code %s", xtid));
	}

}
