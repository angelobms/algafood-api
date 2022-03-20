package com.algaworks.algafood.domain.exception;

public class GroupSystemNotFounException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;
	
	public GroupSystemNotFounException(String message) {
		super(message);
	}
	
	public GroupSystemNotFounException(Long id) {
		this(String.format("There is no system group registration with code %d", id));
	}

}
