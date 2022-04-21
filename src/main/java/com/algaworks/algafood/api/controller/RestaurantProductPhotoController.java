package com.algaworks.algafood.api.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.model.input.ProductPhotoInput;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products/{productId}/photo")
public class RestaurantProductPhotoController {

	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void updatePhoto(@PathVariable Long restaurantId, @PathVariable Long productId,
			ProductPhotoInput productPhotoInput) {

		var fileName = UUID.randomUUID().toString() + "_" + productPhotoInput.getFile().getOriginalFilename();
		var photoFile = Path.of("/home/angelobms/Downloads/upload", fileName);

		System.out.println(productPhotoInput.getDescription());
		System.out.println(photoFile);
		System.out.println(productPhotoInput.getFile().getContentType());

		try {
			productPhotoInput.getFile().transferTo(photoFile);
		} catch (IllegalStateException | IOException e) {
			throw new RuntimeException(e);
		}

	}

}
