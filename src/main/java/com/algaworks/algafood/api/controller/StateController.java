package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.model.State;
import com.algaworks.algafood.domain.repository.StateRepository;
import com.algaworks.algafood.domain.service.StateRegistrationService;

@RestController
@RequestMapping("/states")
public class StateController {

	@Autowired
	private StateRepository stateRepository;
	
	@Autowired
	private StateRegistrationService stateRegistrationService;
	
	@GetMapping
	public List<State> list() {
		return stateRepository.list();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<State> find(@PathVariable Long id) {
		State state = stateRepository.find(id);
		
		if (state != null) {
			return ResponseEntity.ok(state);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public State add(@RequestBody State state) {
		return stateRegistrationService.save(state);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<State> update(@PathVariable Long id, @RequestBody State state) {
		State state2 = stateRepository.find(id);
		
		if (state2 != null) {
			BeanUtils.copyProperties(state, state2, "id");
			
			state2 = stateRegistrationService.save(state2);
			return ResponseEntity.ok(state2);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<State> remove(@PathVariable Long id) {
		try {
			stateRegistrationService.delete(id);
			
			return ResponseEntity.noContent().build();
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
			
		} catch (EntityInUseException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
}
