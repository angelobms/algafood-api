package com.algaworks.algafood.infrastructure.repository.spec;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.algaworks.algafood.domain.model.Restaurant;

public class RestaurantSpecs {
	
	public static Specification<Restaurant> withFreeShipping() {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("freightRate"), BigDecimal.ZERO);
	}
	
	public static Specification<Restaurant> withSimilarName(String name) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name + "%");
	}

}
