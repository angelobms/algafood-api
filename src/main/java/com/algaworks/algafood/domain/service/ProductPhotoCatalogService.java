package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.ProductPhotoNotFoundException;
import com.algaworks.algafood.domain.model.ProductPhoto;
import com.algaworks.algafood.domain.repository.ProductRepository;
import com.algaworks.algafood.domain.service.PhotoStorageService.NewPhoto;

@Service
public class ProductPhotoCatalogService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private PhotoStorageService photoStorageService;

	@Transactional
	public ProductPhoto save(ProductPhoto photo, InputStream fileData) {

		Long restaurantId = photo.getRestaurantId();
		Long photoId = photo.getProduct().getId();
		String newFileName = photoStorageService.createFileName(photo.getFileName());
		String oldFileName = null;

		Optional<ProductPhoto> photoExist = productRepository.findPhotoById(restaurantId, photoId);
		if (photoExist.isPresent()) {
			oldFileName = photoExist.get().getFileName();
			productRepository.delete(photoExist.get());
		}

		photo.setFileName(newFileName);
		photo = productRepository.save(photo);
		productRepository.flush();

		NewPhoto newPhoto = NewPhoto.builder().fileName(photo.getFileName()).inputStream(fileData).build();

		photoStorageService.replace(oldFileName, newPhoto);

		return photo;
	}

	public ProductPhoto findOrFail(Long restaurantId, Long productId) {
		return productRepository.findPhotoById(restaurantId, productId)
				.orElseThrow(() -> new ProductPhotoNotFoundException(restaurantId, productId));
	}

}
