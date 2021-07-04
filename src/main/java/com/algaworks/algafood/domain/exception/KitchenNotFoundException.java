package com.algaworks.algafood.domain.exception;

public class KitchenNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;
	
	private static final String MSG_KITCHEN_NOT_FOUND = "There is no kitchen register with the code %d.";

	public KitchenNotFoundException(String message) {
		super(message);
	}
	
	public KitchenNotFoundException(Long kitchenId) {
		this(String.format(MSG_KITCHEN_NOT_FOUND, kitchenId));
	}
	
}
