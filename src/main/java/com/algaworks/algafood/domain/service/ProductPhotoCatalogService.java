package com.algaworks.algafood.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.ProductPhoto;
import com.algaworks.algafood.domain.repository.ProductRepository;

@Service
public class ProductPhotoCatalogService {
	
	@Autowired
	private ProductRepository productRepository; 
	
	@Transactional
	public ProductPhoto save(ProductPhoto photo) {
		
		Long restaurantId = photo.getRestaurantId();
		Long photoId = photo.getProduct().getId();
		
		Optional<ProductPhoto> photoExist = productRepository.findPhotoById(restaurantId, photoId);
		if (photoExist.isPresent()) {
			productRepository.delete(photoExist.get());
		}
		
		return productRepository.save(photo);
	}

}
