package com.algaworks.algafood.domain.exception;

public class UserSystemNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;
	
	private static final String MSG_STATE_NOT_FOUND = "There is no user system register with the code %d.";
	
	public UserSystemNotFoundException(String message) {
		super(message);
	}
	
	public UserSystemNotFoundException(Long id) {
		this(String.format(MSG_STATE_NOT_FOUND, id));
	}

}
