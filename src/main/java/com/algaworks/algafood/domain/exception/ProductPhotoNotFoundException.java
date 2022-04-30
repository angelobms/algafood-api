package com.algaworks.algafood.domain.exception;

public class ProductPhotoNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public ProductPhotoNotFoundException(String message) {
		super(message);
	}

	public ProductPhotoNotFoundException(Long restaurantId, Long productId) {
		this(String.format("There is no product photo record with code %d for restaurant with code %d", productId, restaurantId));
	}
}
