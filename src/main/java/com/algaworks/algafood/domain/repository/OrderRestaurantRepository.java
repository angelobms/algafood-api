package com.algaworks.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.OrderRestaurant;

@Repository
public interface OrderRestaurantRepository
		extends CustomJpaRepository<OrderRestaurant, Long>, JpaSpecificationExecutor<OrderRestaurant> {

	Optional<OrderRestaurant> findByXtid(String xtid);

	@Query("FROM OrderRestaurant o JOIN FETCH o.customer JOIN FETCH o.restaurant r JOIN FETCH r.kitchen")
	List<OrderRestaurant> findAll();

}
