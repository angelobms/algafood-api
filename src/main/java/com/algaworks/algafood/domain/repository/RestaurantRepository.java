package com.algaworks.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Restaurant;

@Repository
public interface RestaurantRepository extends CustomJpaRepository<Restaurant, Long>, RestaurantRepositoryQueries, JpaSpecificationExecutor<Restaurant> {
	
	@Query("from Restaurant r join fetch r.kitchen join fetch r.paymentMethods")
	List<Restaurant> findAll();
	
	List<Restaurant> queryByFreightRateBetween(BigDecimal freightRateInit, BigDecimal freightRateEnd);
	
//	@Query("from Restaurant where name like %:name% and kitchen.id = :id")
	List<Restaurant> consultByName(String name, @Param("id") Long kitchenId);
	
	List<Restaurant> findByNameContainingAndKitchenId(String name, Long kitchenId);
	
	Optional<Restaurant> findFirstRestaurantByNameContaining(String name);
	
	List<Restaurant> findTop2ByNameContaining(String name);
	
	int countByKitchenId(Long kitchenId);
	
}
