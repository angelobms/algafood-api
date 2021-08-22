package com.algaworks.algafood.api.model.mixin;

import java.time.OffsetDateTime;
import java.util.List;

import com.algaworks.algafood.domain.model.Address;
import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.model.PaymentMethod;
import com.algaworks.algafood.domain.model.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public abstract class RestaurantMixin {

	@JsonIgnoreProperties(value = "name", allowGetters = true)
//	@JsonIgnoreProperties("hibernateLazyInitializer")
//	@JsonIgnore
	private Kitchen kitchen;
	
	@JsonIgnore
	private Address address;
	
	@JsonIgnore
	private OffsetDateTime registrationDate;
	
	@JsonIgnore
	private OffsetDateTime updateDate;
	
	@JsonIgnore
	private List<PaymentMethod> paymentMethods;
	
	@JsonIgnore
	private List<Product> products;
}
