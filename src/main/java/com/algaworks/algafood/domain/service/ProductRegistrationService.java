package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.ProductNotFoundException;
import com.algaworks.algafood.domain.model.Product;
import com.algaworks.algafood.domain.repository.ProductRepository;

@Service
public class ProductRegistrationService {

	@Autowired
	private ProductRepository productRepository;
	
	@Transactional
	public Product save(Product product) {
		return productRepository.save(product);
	}
	
	public Product findOrFail(Long restaurantId, Long productId) {
		return productRepository.findById(restaurantId, productId).orElseThrow(
				() -> new ProductNotFoundException(restaurantId, productId));
	}	
}
