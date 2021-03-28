package com.algaworks.algafood.infrastructure.repository;

import static com.algaworks.algafood.infrastructure.repository.spec.RestaurantSpecs.withFreeShipping;
import static com.algaworks.algafood.infrastructure.repository.spec.RestaurantSpecs.withSimilarName;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.RestaurantRepository;
import com.algaworks.algafood.domain.repository.RestaurantRepositoryQueries;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepositoryQueries {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired @Lazy
	private RestaurantRepository restaurantRepository;
	
	@Override
	public List<Restaurant> find(String name, BigDecimal freightRateInit, BigDecimal freightRateEnd) {
		
//***** JPQL *************************************************************************************************		
//		var jpql = new StringBuilder();
//		jpql.append("from Restaurant where 1 = 1 ");
//		
//		var params = new HashMap<String, Object>();
//		
//		if (StringUtils.hasLength(name)) {
//			jpql.append("and name like :name ");
//			params.put("name", "%" + name + "%");
//		}
//		
//		if (freightRateInit != null) {
//			jpql.append("and freightRate >= :freightRateInit ");
//			params.put("freightRateInit", freightRateInit);
//		}
//		
//		if (freightRateEnd != null) {
//			jpql.append("and freightRate <= :freightRateEnd ");
//			params.put("freightRateEnd", freightRateEnd);
//		}
//		
//		TypedQuery<Restaurant> query = entityManager.createQuery(jpql.toString(), Restaurant.class);
//		
//		params.forEach((key, value) -> query.setParameter(key, value));		
//		
//************************************************************************************************************
		
//***** Criteria API******************************************************************************************		
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Restaurant> criteriaQuery = builder.createQuery(Restaurant.class);
		Root<Restaurant> root = criteriaQuery.from(Restaurant.class); // from Restaurant
		
		var predicates = new ArrayList<Predicate>();
		
		if (StringUtils.hasLength(name)) {
			predicates.add(builder.like(root.get("name"), "%" + name + "%"));
		}
		
		if (freightRateInit != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("freightRate"), freightRateInit));
		}
		
		if (freightRateEnd != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("freightRate"), freightRateEnd));
		}
		
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		
		TypedQuery<Restaurant> query = entityManager.createQuery(criteriaQuery);

//************************************************************************************************************		
		
		return query.getResultList();
	}

	@Override
	public List<Restaurant> findWithFreeShipping(String name) {
		return restaurantRepository.findAll(withFreeShipping().and(withSimilarName(name)));
	}

}
