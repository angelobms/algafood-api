package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.RestaurantRepository;

@Component
public class RestaurantRepositoryImpl implements RestaurantRepository {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Restaurant> list() {
		return manager.createQuery("from Restaurant", Restaurant.class).getResultList();
	}

	@Override
	public Restaurant find(Long id) {
		return manager.find(Restaurant.class, id);
	}

	@Transactional
	@Override
	public Restaurant save(Restaurant restaurant) {
		return manager.merge(restaurant);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		Restaurant restaurant = find(id);
		
		if (restaurant == null) {
			throw new EmptyResultDataAccessException(1);
		}
		manager.remove(restaurant);
	}

}
