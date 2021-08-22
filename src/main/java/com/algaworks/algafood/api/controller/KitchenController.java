package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.KitchenInputDisassembler;
import com.algaworks.algafood.api.assembler.KitchenModelAssembler;
import com.algaworks.algafood.api.model.KitchenModel;
import com.algaworks.algafood.api.model.KitchensXmlWrapper;
import com.algaworks.algafood.api.model.input.KitchenInput;
import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.repository.KitchenRepository;
import com.algaworks.algafood.domain.service.KitchenRegistrationService;

@RestController
@RequestMapping("/kitchens")
public class KitchenController {
	
	@Autowired
	private KitchenRepository kitchenRepository;
	
	@Autowired
	private KitchenRegistrationService kitchenRegistrationService;
	
	@Autowired
	private KitchenModelAssembler kitchenModelAssembler;
	
	@Autowired
	private KitchenInputDisassembler kitchenInputDisassembler; 
	
	@GetMapping
	public List<KitchenModel> list() {
		return kitchenModelAssembler.toCollectionModel(kitchenRepository.findAll());
	}
	
	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public KitchensXmlWrapper listXml() {
		return new KitchensXmlWrapper(kitchenRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public KitchenModel find(@PathVariable("id") Long id) {
		return kitchenModelAssembler.toModel(kitchenRegistrationService.findOrFail(id));	
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public KitchenModel add(@RequestBody @Valid KitchenInput kitchenInput) {
		Kitchen kitchen = kitchenInputDisassembler.toDomainObject(kitchenInput);

		kitchen = kitchenRegistrationService.save(kitchen);
		
		return kitchenModelAssembler.toModel(kitchen);
	}
	
	@PutMapping("/{id}")
	public KitchenModel update(@PathVariable Long id, @RequestBody @Valid KitchenInput kitchenInput) {
		Kitchen kitchen2 = kitchenRegistrationService.findOrFail(id);
		
//		BeanUtils.copyProperties(kitchen, kitchen2, "id");
		kitchenInputDisassembler.copyToDomainObject(kitchenInput, kitchen2);

		kitchen2 = kitchenRegistrationService.save(kitchen2); 
		
		return kitchenModelAssembler.toModel(kitchen2);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long id){		
		kitchenRegistrationService.delete(id);					
	}

}
