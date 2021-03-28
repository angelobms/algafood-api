package com.algaworks.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Kitchen;

@Repository
public interface KitchenRepository extends JpaRepository<Kitchen, Long>{
	
	List<Kitchen> findAllByNameContaining(String name);
	
	Optional<Kitchen> findByName(String name);
	
	boolean existsByName(String name);
	
}
