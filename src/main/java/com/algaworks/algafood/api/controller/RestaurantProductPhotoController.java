package com.algaworks.algafood.api.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.algafood.api.assembler.ProductPhotoModelAssembler;
import com.algaworks.algafood.api.model.ProductPhotoModel;
import com.algaworks.algafood.api.model.input.ProductPhotoInput;
import com.algaworks.algafood.domain.model.Product;
import com.algaworks.algafood.domain.model.ProductPhoto;
import com.algaworks.algafood.domain.service.ProductPhotoCatalogService;
import com.algaworks.algafood.domain.service.ProductRegistrationService;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products/{productId}/photo")
public class RestaurantProductPhotoController {
	
	@Autowired
	private ProductRegistrationService productRegistrationService;
	
	@Autowired
	private ProductPhotoCatalogService productPhotoCatalogService;
	
	@Autowired
	private ProductPhotoModelAssembler productPhotoModelAssembler;

	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ProductPhotoModel updatePhoto(@PathVariable Long restaurantId, @PathVariable Long productId,
			@Valid ProductPhotoInput productPhotoInput) throws IOException {

//		var fileName = UUID.randomUUID().toString() + "_" + productPhotoInput.getFile().getOriginalFilename();
//		var photoFile = Path.of("/home/angelobms/Downloads/upload", fileName);
//
//		System.out.println(productPhotoInput.getDescription());
//		System.out.println(photoFile);
//		System.out.println(productPhotoInput.getFile().getContentType());
//
//		try {
//			productPhotoInput.getFile().transferTo(photoFile);
//		} catch (IllegalStateException | IOException e) {
//			throw new RuntimeException(e);
//		}
		
		Product product = productRegistrationService.findOrFail(restaurantId, productId);
		
		MultipartFile file = productPhotoInput.getFile();
		
		ProductPhoto photo = new ProductPhoto();
		photo.setProduct(product);
		photo.setDescription(productPhotoInput.getDescription());
		photo.setContentType(file.getContentType());
		photo.setSize(file.getSize());
		photo.setFileName(file.getOriginalFilename());
		
		ProductPhoto productPhoto = productPhotoCatalogService.save(photo, file.getInputStream());
		
		return productPhotoModelAssembler.toModel(productPhoto);
	}
	
	@GetMapping
	public ProductPhotoModel find(@PathVariable Long restaurantId, @PathVariable Long productId) {
		ProductPhoto photo = productPhotoCatalogService.findOrFail(restaurantId, productId);
		return productPhotoModelAssembler.toModel(photo);
	}

}
