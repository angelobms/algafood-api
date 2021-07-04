package com.algaworks.algafood.domain.exception;

public class StateNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;
	
	private static final String MSG_STATE_NOT_FOUND = "There is no state register with the code %d.";

	public StateNotFoundException(String message) {
		super(message);
	}
	
	public StateNotFoundException(Long stateId) {
		this(String.format(MSG_STATE_NOT_FOUND, stateId));
	}
	
}
