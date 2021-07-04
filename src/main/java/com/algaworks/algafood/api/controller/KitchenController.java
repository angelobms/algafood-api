package com.algaworks.algafood.api.controller;

import java.util.List;

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

import com.algaworks.algafood.api.model.KitchensXmlWrapper;
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
	
	@GetMapping
	public List<Kitchen> list() {
		return kitchenRepository.findAll();
	}
	
	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public KitchensXmlWrapper listXml() {
		return new KitchensXmlWrapper(kitchenRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public Kitchen find(@PathVariable("id") Long id) {
		return kitchenRegistrationService.findOrFail(id);	
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Kitchen add(@RequestBody Kitchen kitchen) {
		return kitchenRegistrationService.save(kitchen);
	}
	
	@PutMapping("/{id}")
	public Kitchen update(@PathVariable Long id, @RequestBody Kitchen kitchen) {
		Kitchen kitchen2 = kitchenRegistrationService.findOrFail(id);

		BeanUtils.copyProperties(kitchen, kitchen2, "id");

		return kitchenRegistrationService.save(kitchen2);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long id){		
		kitchenRegistrationService.delete(id);					
	}

}
