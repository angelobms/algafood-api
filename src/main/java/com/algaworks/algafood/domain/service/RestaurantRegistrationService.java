package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.RestaurantNotFoundException;
import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.RestaurantRepository;

@Service
public class RestaurantRegistrationService {

	@Autowired
	RestaurantRepository restaurantRepository;

	@Autowired
	KitchenRegistrationService kitchenRegistrationService;

	@Transactional
	public Restaurant save(Restaurant restaurant) {
		Long kitchenId = restaurant.getKitchen().getId();

		Kitchen kitchen = kitchenRegistrationService.findOrFail(kitchenId);

		restaurant.setKitchen(kitchen);

		return restaurantRepository.save(restaurant);
	}

	@Transactional
	public void delete(Long restaurantId) {
		try {
			restaurantRepository.deleteById(restaurantId);
			restaurantRepository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new RestaurantNotFoundException(restaurantId);

		}
	}

	public Restaurant findOrFail(Long restaurantId) {
		return restaurantRepository.findById(restaurantId)
				.orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
	}

}
