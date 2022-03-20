package com.algaworks.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.api.model.AddressModel;
import com.algaworks.algafood.api.model.input.OrderItemInput;
import com.algaworks.algafood.domain.model.Address;
import com.algaworks.algafood.domain.model.OrderItem;


@Configuration
public class ModelMapperConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		
		var addressToToAddressModelTypeMap = modelMapper.createTypeMap(Address.class, AddressModel.class);
		addressToToAddressModelTypeMap.<String>addMapping(
				address -> address.getCity().getState().getName(), 
				(addressModel, value) -> addressModel.getCity().setState(value));
		
		modelMapper.createTypeMap(OrderItemInput.class, OrderItem.class)
	    	.addMappings(mapper -> mapper.skip(OrderItem::setId));  
		
		return modelMapper;
	}

}
