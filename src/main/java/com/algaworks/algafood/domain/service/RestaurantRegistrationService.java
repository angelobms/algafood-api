package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.KitchenRepository;
import com.algaworks.algafood.domain.repository.RestaurantRepository;

@Service
public class RestaurantRegistrationService {
	
	@Autowired
	RestaurantRepository restaurantRepository;
	
	@Autowired
	KitchenRepository kitchenRepository;
	
	public Restaurant save(Restaurant restaurant) {
		Long kitchenId = restaurant.getKitchen().getId();
		Kitchen kitchen = kitchenRepository.find(kitchenId);
		
		if (kitchen == null) {
			throw new EntityNotFoundException(
					String.format("There is no kitchen register with the code %d.", kitchenId));
		}
		
		restaurant.setKitchen(kitchen);
		
		return restaurantRepository.save(restaurant);
	}
	
	public void delete(Long restaurantId) {
		try {
			restaurantRepository.delete(restaurantId);
			
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException(
					String.format("There is no restaurant register with the code %d.", restaurantId));	
			
		} 
	}

}
