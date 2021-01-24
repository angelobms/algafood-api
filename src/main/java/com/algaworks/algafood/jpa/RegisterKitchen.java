package com.algaworks.algafood.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.model.Kitchen;

@Component
public class RegisterKitchen {

	@PersistenceContext
	private EntityManager manager;
	
	public List<Kitchen> listar() {
		return manager.createQuery("from Kitchen", Kitchen.class).getResultList();
	}
}
