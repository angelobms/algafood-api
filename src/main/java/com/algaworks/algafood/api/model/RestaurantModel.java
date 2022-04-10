package com.algaworks.algafood.api.model;

import java.math.BigDecimal;

import com.algaworks.algafood.api.model.view.RestaurantView;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantModel {

	@JsonView({RestaurantView.Summary.class, RestaurantView.OnlyName.class})
	private Long id;
	
	@JsonView({RestaurantView.Summary.class, RestaurantView.OnlyName.class})
	private String name;
	
	@JsonView(RestaurantView.Summary.class)
	private BigDecimal freightRate;
	
	@JsonView(RestaurantView.Summary.class)
	private KitchenModel kitchen;
	
	private Boolean active;
	private Boolean open;
	private AddressModel address;
	
}
