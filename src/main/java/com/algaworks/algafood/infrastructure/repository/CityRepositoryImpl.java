package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.City;
import com.algaworks.algafood.domain.repository.CityRepositpry;

@Component
public class CityRepositoryImpl implements CityRepositpry {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<City> list() {
		return manager.createQuery("from City", City.class).getResultList();
	}

	@Override
	public City find(Long id) {
		return manager.find(City.class, id);
	}

	@Transactional
	@Override
	public City save(City city) {
		return manager.merge(city);
	}

	@Transactional
	@Override
	public void delete(City city) {
		city = find(city.getId());
		manager.remove(city);
	}

}
