package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.OrderSummaryModel;
import com.algaworks.algafood.domain.model.OrderRestaurant;

@Component
public class OrderSummaryAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public OrderSummaryModel toModel(OrderRestaurant orderRestaurant) {
		return modelMapper.map(orderRestaurant, OrderSummaryModel.class);
	}
	
	public List<OrderSummaryModel> toCollectionModel(List<OrderRestaurant> orderRestaurants) {
		return orderRestaurants.stream().map(order -> toModel(order)).collect(Collectors.toList());
	}
}
