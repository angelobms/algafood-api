package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.algaworks.algafood.api.model.KitchensXmlWrapper;
import com.algaworks.algafood.domain.exception.EntityInUseException;
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
		return kitchenRepository.list();
	}
	
	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public KitchensXmlWrapper listXml() {
		return new KitchensXmlWrapper(kitchenRepository.list());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Kitchen> find(@PathVariable("id") Long id) {
		Kitchen kitchen = kitchenRepository.find(id);
		
		if (kitchen != null) {
			return ResponseEntity.ok(kitchen); 
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Kitchen add(@RequestBody Kitchen kitchen) {
		return kitchenRegistrationService.save(kitchen);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Kitchen> update(@PathVariable Long id, @RequestBody Kitchen kitchen) {
		Kitchen kitchen2 = kitchenRepository.find(id);
		
		if (kitchen2 != null) {
			BeanUtils.copyProperties(kitchen, kitchen2, "id");
			
			kitchen2 = kitchenRegistrationService.save(kitchen2);
			return ResponseEntity.ok(kitchen2);			
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Kitchen> remove(@PathVariable Long id){
		try {
			kitchenRegistrationService.delete(id);
			
			return ResponseEntity.noContent().build();
			
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
			
		} catch (EntityInUseException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

}
