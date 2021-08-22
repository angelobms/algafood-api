package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.CityInput;
import com.algaworks.algafood.domain.model.City;
import com.algaworks.algafood.domain.model.State;

@Component
public class CityInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public City toDomainObject(CityInput cityInput) {
		return modelMapper.map(cityInput, City.class);
	}
	
	public void copyToDomainObject(CityInput cityInput, City city) {
		city.setState(new State());
		
		modelMapper.map(cityInput, city);
	}
	
}
