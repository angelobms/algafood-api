package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.KitchenNotFoundException;
import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.repository.KitchenRepository;

@Service
public class KitchenRegistrationService {

	private static final String MSG_KITCHEN_IN_USE = "Kitchen of code %d cannot be removed because it is in use.";

	@Autowired
	KitchenRepository kitchenRepository;

	public Kitchen save(Kitchen kitchen) {
		return kitchenRepository.save(kitchen);
	}

	public void delete(Long kitchenId) {
		try {
			kitchenRepository.deleteById(kitchenId);

		} catch (EmptyResultDataAccessException e) {
			throw new KitchenNotFoundException(kitchenId);

		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format(MSG_KITCHEN_IN_USE, kitchenId));
		}
	}

	public Kitchen findOrFail(Long kitchenId) {
		return kitchenRepository.findById(kitchenId).orElseThrow(() -> new KitchenNotFoundException(kitchenId));
	}

}
