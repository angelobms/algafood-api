package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.City;
import com.algaworks.algafood.domain.model.State;
import com.algaworks.algafood.domain.repository.CityRepositpry;
import com.algaworks.algafood.domain.repository.StateRepository;

@Service
public class CityRegistrationService {

	@Autowired
	private StateRepository stateRepository;
	
	@Autowired
	private CityRepositpry cityRepositpry;
	
	public City save(City city) {
		Long stateId = city.getState().getId();
		State state = stateRepository.find(stateId);
		
		if (state == null) {
			throw new EntityNotFoundException(
					String.format("There is no state register with the code %d.", stateId));
		}
		
		city.setState(state);
		
		return cityRepositpry.save(city);
	}
	
	public void delete(Long cityId) {
		try {
			cityRepositpry.delete(cityId);
			
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException(
					String.format("There is no city register with the code %d.", cityId));	
			
		} 
	}
}
