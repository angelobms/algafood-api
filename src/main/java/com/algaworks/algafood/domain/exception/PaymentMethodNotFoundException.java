package com.algaworks.algafood.domain.exception;

public class PaymentMethodNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;
	
	public PaymentMethodNotFoundException(String message) {
		super(message);
	}
	
	public PaymentMethodNotFoundException(Long id) {
		this(String.format("There is no payment method registration with code %d", id));
	}

}
