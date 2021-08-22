package com.algaworks.algafood.api.model.mixin;

import java.util.List;

import com.algaworks.algafood.domain.model.Restaurant;
import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class KitchenMixin {
	
	@JsonIgnore
	private List<Restaurant> restaurants;

}
