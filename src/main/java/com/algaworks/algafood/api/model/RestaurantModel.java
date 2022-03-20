package com.algaworks.algafood.api.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantModel {

	private Long id;
	private String name;
	private BigDecimal freightRate;
	private KitchenModel kitchen;
	private Boolean active;
	private Boolean open;
	private AddressModel address;
	
}
