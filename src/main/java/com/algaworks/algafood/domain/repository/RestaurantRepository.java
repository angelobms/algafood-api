package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.Restaurant;

public interface RestaurantRepository {
	
	List<Restaurant> list();
	Restaurant find(Long id);
	Restaurant save(Restaurant restaurant);
	void delete(Long id);

}
