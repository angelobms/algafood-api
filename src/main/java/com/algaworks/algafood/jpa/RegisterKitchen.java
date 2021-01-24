package com.algaworks.algafood.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Kitchen;

@Component
public class RegisterKitchen {

	@PersistenceContext
	private EntityManager manager;
	
	public List<Kitchen> listar() {
		return manager.createQuery("from Kitchen", Kitchen.class).getResultList();
	}
	
	public Kitchen find(Long id) {
		return manager.find(Kitchen.class, id);
	}
	
	@Transactional
	public Kitchen add(Kitchen kitchen) {
		return manager.merge(kitchen);
	}
	
	@Transactional
	public void delete(Kitchen kitchen) {
		kitchen = find(kitchen.getId());
		manager.remove(kitchen);
	}
}