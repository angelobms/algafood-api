package com.algaworks.algafood.infrastructure.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.ProductPhoto;
import com.algaworks.algafood.domain.repository.ProductRepositoryQueries;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryQueries {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	@Override
	public ProductPhoto save(ProductPhoto photo) {
		return entityManager.merge(photo);
	}

	@Transactional
	@Override
	public void delete(ProductPhoto photo) {
		entityManager.remove(photo);		
	}

}
