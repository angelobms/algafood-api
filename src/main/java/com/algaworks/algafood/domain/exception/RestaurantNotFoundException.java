package com.algaworks.algafood.domain.exception;

public class RestaurantNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;
	
	private static final String MSG_RESTAURANT_NOT_FOUND = "There is no restaurant register with the code %d.";

	public RestaurantNotFoundException(String message) {
		super(message);
	}
	
	public RestaurantNotFoundException(Long restaurantId) {
		this(String.format(MSG_RESTAURANT_NOT_FOUND, restaurantId));
	}
	
}
