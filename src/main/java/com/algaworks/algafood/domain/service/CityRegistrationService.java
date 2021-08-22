package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.CityNotFoundException;
import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.City;
import com.algaworks.algafood.domain.model.State;
import com.algaworks.algafood.domain.repository.CityRepositpry;

@Service
public class CityRegistrationService {

	private static final String MSG_CITY_IN_USE = "City of code %d cannot be removed because it is in use.";

	@Autowired
	private StateRegistrationService stateRegistrationService;

	@Autowired
	private CityRepositpry cityRepositpry;

	@Transactional
	public City save(City city) {
		Long stateId = city.getState().getId();

		State state = stateRegistrationService.findOrFail(stateId);

		city.setState(state);

		return cityRepositpry.save(city);
	}

	@Transactional
	public void delete(Long cityId) {
		try {
			cityRepositpry.deleteById(cityId);
			cityRepositpry.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new CityNotFoundException(cityId);

		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format(MSG_CITY_IN_USE, cityId));
		}
	}

	public City findOrFail(Long cityId) {
		return cityRepositpry.findById(cityId).orElseThrow(() -> new CityNotFoundException(cityId));
	}
}
