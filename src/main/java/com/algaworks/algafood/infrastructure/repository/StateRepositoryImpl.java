package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.State;
import com.algaworks.algafood.domain.repository.StateRepository;

@Component
public class StateRepositoryImpl implements StateRepository {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<State> list() {
		return manager.createQuery("from State", State.class).getResultList();
	}

	@Override
	public State find(Long id) {
		return manager.find(State.class, id);
	}

	@Transactional
	@Override
	public State save(State state) {
		return manager.merge(state);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		State state = find(id);
		
		if (state == null) {
			throw new EmptyResultDataAccessException(1);
		}
		manager.remove(state);
	}

}
