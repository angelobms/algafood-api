package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.RestaurantModel;
import com.algaworks.algafood.domain.model.Restaurant;

@Component
public class RestaurantModelAssembler {
	
	@Autowired
	ModelMapper modelMapper;
	
	public RestaurantModel toModel(Restaurant restaurant) {
		return modelMapper.map(restaurant, RestaurantModel.class);
	}
	
	public List<RestaurantModel> toCollectionModel(List<Restaurant> restaurants) {
		return restaurants.stream()
				.map(restaurant -> toModel(restaurant))
				.collect(Collectors.toList());
	}

}
