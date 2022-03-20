package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.RestaurantNotFoundException;
import com.algaworks.algafood.domain.model.City;
import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.model.PaymentMethod;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.model.UserSystem;
import com.algaworks.algafood.domain.repository.RestaurantRepository;

@Service
public class RestaurantRegistrationService {

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private KitchenRegistrationService kitchenRegistrationService;
	
	@Autowired
	private CityRegistrationService cityRegistrationService; 
	
	@Autowired
	private PaymentMethodRegistrationService paymentMethodRegistrationService;
	
	@Autowired
	private UserSystemRegistrationService userSystemRegistration; 

	@Transactional
	public Restaurant save(Restaurant restaurant) {
		Long kitchenId = restaurant.getKitchen().getId();
		Kitchen kitchen = kitchenRegistrationService.findOrFail(kitchenId);
		restaurant.setKitchen(kitchen);

		Long cityId = restaurant.getAddress().getCity().getId(); 
		City city = cityRegistrationService.findOrFail(cityId);
		restaurant.getAddress().setCity(city);

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
	
	@Transactional
	public void activate(Long restaurantId) {
		Restaurant restaurant = findOrFail(restaurantId);
		restaurant.activate();
	}
	
	@Transactional
	public void inactivate(Long restaurantId) {
		Restaurant restaurant = findOrFail(restaurantId);
		restaurant.inactivate();
	}
	
	@Transactional
	public void activate(List<Long> restaurantIds) {
		restaurantIds.forEach(this::activate);
	}
	
	@Transactional
	public void inactivate(List<Long> restaurantIds) {
		restaurantIds.forEach(this::inactivate);
	}
	
	@Transactional
	public void associatePaymentMethod(Long restaurantId, Long paymentMethodId) {
		Restaurant restaurant = findOrFail(restaurantId);
		PaymentMethod paymentMethod = paymentMethodRegistrationService.findOrFail(paymentMethodId);
		
		restaurant.addPaymentMethod(paymentMethod);
	}
	
	@Transactional
	public void disassociatePaymentMethod(Long restaurantId, Long paymentMethodId) {
		Restaurant restaurant = findOrFail(restaurantId);
		PaymentMethod paymentMethod = paymentMethodRegistrationService.findOrFail(paymentMethodId);
		
		restaurant.removePaymentMethod(paymentMethod);
	}

	public Restaurant findOrFail(Long restaurantId) {
		return restaurantRepository.findById(restaurantId)
				.orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
	}
	
	@Transactional
	public void open(Long restaurantId) {
		Restaurant restaurant = findOrFail(restaurantId);
		restaurant.open();
	}
	
	@Transactional
	public void close(Long restaurantId) {
		Restaurant restaurant = findOrFail(restaurantId);
		restaurant.close();
	}
	
	@Transactional
	public void associateResponsable(Long restaurantId, Long userId) {
		Restaurant restaurant = findOrFail(restaurantId);
	    UserSystem user = userSystemRegistration.findOrFail(userId);
	    
	    restaurant.addResponsible(user);
	}
	
	@Transactional
	public void disassociateResponsable(Long restaurantId, Long userId) {
	    Restaurant restaurant = findOrFail(restaurantId);
	    UserSystem user = userSystemRegistration.findOrFail(userId);
	    
	    restaurant.removeResponsible(user);
	}
	
}
