package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.StateNotFoundException;
import com.algaworks.algafood.domain.model.State;
import com.algaworks.algafood.domain.repository.StateRepository;

@Service
public class StateRegistrationService {

	private static final String MSG_STATE_IN_USE = "State of code %d cannot be removed because it is in use.";

	@Autowired
	StateRepository stateRepository;

	public State save(State state) {
		return stateRepository.save(state);
	}

	public void delete(Long stateId) {
		try {
			stateRepository.deleteById(stateId);

		} catch (EmptyResultDataAccessException e) {
			throw new StateNotFoundException(stateId);

		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format(MSG_STATE_IN_USE, stateId));
		}
	}

	public State findOrFail(Long stateId) {
		return stateRepository.findById(stateId).orElseThrow(() -> new StateNotFoundException(stateId));
	}

}
