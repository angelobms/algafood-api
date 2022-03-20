package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.ProductInputDisassembler;
import com.algaworks.algafood.api.assembler.ProductModelAssembler;
import com.algaworks.algafood.api.model.ProductModel;
import com.algaworks.algafood.api.model.input.ProductInput;
import com.algaworks.algafood.domain.model.Product;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.ProductRepository;
import com.algaworks.algafood.domain.service.ProductRegistrationService;
import com.algaworks.algafood.domain.service.RestaurantRegistrationService;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products")
public class RestaurantProductController {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductRegistrationService productRegistration;
	
	@Autowired
	private RestaurantRegistrationService restaurantRegistration;
	
	@Autowired
	private ProductModelAssembler productModelAssembler;
	
	@Autowired
	private ProductInputDisassembler productInputDisassembler;
	
	@GetMapping
	public List<ProductModel> list(@PathVariable Long restaurantId) {
		Restaurant restaurant = restaurantRegistration.findOrFail(restaurantId);
		
		List<Product> allProducts = productRepository.findByRestaurant(restaurant);
		
		return productModelAssembler.toCollectionModel(allProducts);
	}
	
	@GetMapping("/{productId}")
	public ProductModel find(@PathVariable Long restaurantId, @PathVariable Long productId) {
		Product product = productRegistration.findOrFail(restaurantId, productId);
		
		return productModelAssembler.toModel(product);				
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)	
	public ProductModel add(@PathVariable Long restaurantId, @RequestBody @Valid ProductInput productInput) {
		Restaurant restaurant = restaurantRegistration.findOrFail(restaurantId);
		
		Product product = productInputDisassembler.toDomainObject(productInput);
		product.setRestaurant(restaurant);
		
		product = productRegistration.save(product);
		
		return productModelAssembler.toModel(product);
	}
	
	@PutMapping("/{productId}")
	public ProductModel update(@PathVariable Long restaurantId, @PathVariable Long productId, @RequestBody @Valid ProductInput productInput) {
		Product product = productRegistration.findOrFail(restaurantId, productId);
		
		productInputDisassembler.copyToDomainObject(productInput, product);
		
		product = productRegistration.save(product);
		
		return productModelAssembler.toModel(product);
	}
}
