package com.algaworks.algafood.infrastructure.repository.spec;

import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.algaworks.algafood.domain.filter.OrderFilter;
import com.algaworks.algafood.domain.model.OrderRestaurant;


public class OrderSpecs {
	
	public static Specification<OrderRestaurant> usingFilter(OrderFilter filter) {
		return (root, query, criteriaBuilder)-> {	
			if (OrderRestaurant.class.equals(query.getResultType())) {
				root.fetch("restaurant").fetch("kitchen");
				root.fetch("customer");
			}
			
			var predicates = new ArrayList<Predicate>();
			
			if (filter.getClientId() != null) {
				predicates.add(criteriaBuilder.equal(root.get("customer"), filter.getClientId()));
			}
			
			if (filter.getRestaurantId() != null) {
				predicates.add(criteriaBuilder.equal(root.get("restaurant"), filter.getRestaurantId()));
			}
			
			if (filter.getStartRegistrationDate() != null) {
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("registrationDate"), filter.getStartRegistrationDate()));
			}
			
			if (filter.getEndRegistrationDate() != null) {
				predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("registrationDate"), filter.getEndRegistrationDate()));
			}
			
			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
	}

}
