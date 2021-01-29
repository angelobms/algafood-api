package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.City;

public interface CityRepositpry {

	List<City> list();
	City find(Long id);
	City save(City city);
	void delete(City city);
}
