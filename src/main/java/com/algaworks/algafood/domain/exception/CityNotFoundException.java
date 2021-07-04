package com.algaworks.algafood.domain.exception;

public class CityNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;
	
	private static final String MSG_CITY_NOT_FOUND = "There is no city register with the code %d.";

	public CityNotFoundException(String message) {
		super(message);
	}
	
	public CityNotFoundException(Long cityId) {
		this(String.format(MSG_CITY_NOT_FOUND, cityId));
	}
	
}
