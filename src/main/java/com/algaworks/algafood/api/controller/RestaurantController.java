package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.RestaurantRepository;
import com.algaworks.algafood.domain.service.RestaurantRegistrationService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

	@Autowired
	RestaurantRepository restaurantRepository;
	
	@Autowired
	RestaurantRegistrationService restaurantRegistrationService;   
	
	@GetMapping
	public List<Restaurant> list() {
		return restaurantRepository.list();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Restaurant> find(@PathVariable Long id) {
		Restaurant restaurant = restaurantRepository.find(id);
		
		if (restaurant != null) {
			return ResponseEntity.ok(restaurant);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<?> add(@RequestBody Restaurant restaurant) {
		try {
			restaurant = restaurantRegistrationService.save(restaurant);
			return ResponseEntity.status(HttpStatus.CREATED).body(restaurant);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Restaurant restaurant) {
		try {
			Restaurant restaurant2 = restaurantRepository.find(id);
			
			if (restaurant2 != null) {
				BeanUtils.copyProperties(restaurant, restaurant2, "id");
				
				restaurant2 = restaurantRegistrationService.save(restaurant2);
				return ResponseEntity.ok(restaurant2);			
			}
			
			return ResponseEntity.notFound().build();			
		} catch (EntityNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> updatePartial(@PathVariable Long id, @RequestBody Map<String, Object> fields) {
		Restaurant restaurant = restaurantRepository.find(id);
		
		if (restaurant == null) {
			return ResponseEntity.notFound().build();
		}
		
		merge(fields, restaurant);
		
		return update(id, restaurant);
	}

	private void merge(Map<String, Object> originFields, Restaurant targetRestaurant) {
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurant originRestaurant = objectMapper.convertValue(originFields, Restaurant.class);
		
		originFields.forEach((propertieName, propertieValue) -> {
			Field field = ReflectionUtils.findField(Restaurant.class, propertieName);
			field.setAccessible(true);
			
			Object valueNew = ReflectionUtils.getField(field, originRestaurant);
			ReflectionUtils.setField(field, targetRestaurant, valueNew);
		});
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Kitchen> remove(@PathVariable Long id){
		try {
			restaurantRegistrationService.delete(id);
			
			return ResponseEntity.noContent().build();
			
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
			
		} 
	}
	
}
