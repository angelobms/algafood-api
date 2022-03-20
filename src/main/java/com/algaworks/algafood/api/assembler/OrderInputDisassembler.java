package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.OrderInput;
import com.algaworks.algafood.domain.model.OrderRestaurant;

@Component
public class OrderInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public OrderRestaurant toDomainObject(OrderInput orderInput) {
		return modelMapper.map(orderInput, OrderRestaurant.class);
	}
	
	public void copyToDomainObject(OrderInput orderInput, OrderRestaurant orderRestaurant) {
		modelMapper.map(orderInput, orderRestaurant);
	}

}
