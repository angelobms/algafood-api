package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.algaworks.algafood.api.assembler.StateInputDisassembler;
import com.algaworks.algafood.api.assembler.StateModelAssembler;
import com.algaworks.algafood.api.model.StateModel;
import com.algaworks.algafood.api.model.input.StateInput;
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
	
	@Autowired
	private StateModelAssembler stateModelAssembler;
	
	@Autowired
	private StateInputDisassembler stateInputDisassembler; 

	@GetMapping
	public List<StateModel> list() {
		return stateModelAssembler.toCollectionModel(stateRepository.findAll());
	}

	@GetMapping("/{id}")
	public StateModel find(@PathVariable Long id) {
		return stateModelAssembler.toModel(stateRegistrationService.findOrFail(id));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public StateModel add(@RequestBody @Valid StateInput stateInput) {
		State state = stateInputDisassembler.toDomainObject(stateInput);
		
		state = stateRegistrationService.save(state);
		
		return stateModelAssembler.toModel(state);
	}

	@PutMapping("/{id}")
	public StateModel update(@PathVariable Long id, @RequestBody @Valid StateInput stateInput) {
		State state2 = stateRepository.findById(id).orElse(null);
		
		stateInputDisassembler.copyToDomainObject(stateInput, state2);
//		BeanUtils.copyProperties(state, state2, "id");

		state2 = stateRegistrationService.save(state2);
		
		return stateModelAssembler.toModel(state2);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long id) {
		stateRegistrationService.delete(id);
	}

}
