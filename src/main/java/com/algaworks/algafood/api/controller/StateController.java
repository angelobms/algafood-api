package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
		return stateRepository.findAll();
	}

	@GetMapping("/{id}")
	public State find(@PathVariable Long id) {
		return stateRegistrationService.findOrFail(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public State add(@RequestBody State state) {
		return stateRegistrationService.save(state);
	}

	@PutMapping("/{id}")
	public State update(@PathVariable Long id, @RequestBody State state) {
		State state2 = stateRepository.findById(id).orElse(null);

		BeanUtils.copyProperties(state, state2, "id");

		return stateRegistrationService.save(state2);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long id) {
		stateRegistrationService.delete(id);
	}

}
