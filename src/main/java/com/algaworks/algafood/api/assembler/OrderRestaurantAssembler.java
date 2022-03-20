package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.OrderRestaurantModel;
import com.algaworks.algafood.domain.model.OrderRestaurant;

@Component
public class OrderRestaurantAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public OrderRestaurantModel toModel(OrderRestaurant orderRestaurant) {
		return modelMapper.map(orderRestaurant, OrderRestaurantModel.class);
	}
	
	public List<OrderRestaurantModel> toCollectionModel(List<OrderRestaurant> orderRestaurants) {
		return orderRestaurants.stream().map(order -> toModel(order)).collect(Collectors.toList());
	}
}
