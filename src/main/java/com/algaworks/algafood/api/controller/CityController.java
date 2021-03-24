package com.algaworks.algafood.api.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.City;
import com.algaworks.algafood.domain.repository.CityRepositpry;
import com.algaworks.algafood.domain.service.CityRegistrationService;

@RestController
@RequestMapping("/cities")
public class CityController {
	
	@Autowired
	private CityRepositpry cityRepositpry;

	@Autowired
	private CityRegistrationService cityRegistrationService;
	
	@GetMapping
	public List<City> list() {
		return cityRepositpry.list();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<City> find(@PathVariable Long id) {
		City city = cityRepositpry.find(id);
		
		if (city != null) {
			return ResponseEntity.ok(city);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<?> add(@RequestBody City city) {
		try {
			city = cityRegistrationService.save(city);
			return ResponseEntity.status(HttpStatus.CREATED).body(city);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody City city) {
		try {
			City city2 = cityRepositpry.find(id);
			
			if (city2 != null) {
				BeanUtils.copyProperties(city, city2, "id");
				
				city2 = cityRegistrationService.save(city2);
				return ResponseEntity.ok(city2);
			}
			
			return ResponseEntity.notFound().build();
		} catch (EntityNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<City> remove(@PathVariable Long id){
		try {
			cityRegistrationService.delete(id);
			
			return ResponseEntity.noContent().build();
			
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
			
		} 
	}
}
