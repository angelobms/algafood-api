package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.algaworks.algafood.api.assembler.RestaurantInputDisassembler;
import com.algaworks.algafood.api.assembler.RestaurantModelAssembler;
import com.algaworks.algafood.api.model.RestaurantModel;
import com.algaworks.algafood.api.model.input.RestaurantInput;
import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.KitchenNotFoundException;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.RestaurantRepository;
import com.algaworks.algafood.domain.service.RestaurantRegistrationService;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

	@Autowired
	RestaurantRepository restaurantRepository;

	@Autowired
	RestaurantRegistrationService restaurantRegistrationService;
	
	@Autowired
	RestaurantModelAssembler restaurantModelAssemebler;
	
	@Autowired
	RestaurantInputDisassembler restaurantInputDisassembler;
	
//	@Autowired
//	private SmartValidator smartValidator;

	@GetMapping
	public List<RestaurantModel	> list() {
		return restaurantModelAssemebler.toCollectionModel(restaurantRepository.findAll());
	}

	@GetMapping("/{id}")
	public RestaurantModel find(@PathVariable Long id) {
		Restaurant restaurant = restaurantRegistrationService.findOrFail(id);
		return restaurantModelAssemebler.toModel(restaurant);
	}

//	@GetMapping("/with-free-shipping")
//	public List<Restaurant> restaurantWithFreeShipping(String name) {
//
////		var withFreeShipping = new RestaurantWithFreeShippingSpec();
////		var withSimilarName = new RestaurantWithSimilarNameSpec(name);
//
////		return restaurantRepository.findAll(withFreeShipping().and(withSimilarName(name)));
//
//		return restaurantRepository.findWithFreeShipping(name);
//	}

//	@GetMapping("/first")
//	public Optional<Restaurant> restaurantFirst() {
//		return restaurantRepository.findFirst();
//	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestaurantModel add(@RequestBody @Valid RestaurantInput restaurantInput) {
		try {
			Restaurant restaurant = restaurantInputDisassembler.toDomainObject(restaurantInput);
			
			return restaurantModelAssemebler.toModel(restaurantRegistrationService.save(restaurant));
		} catch (KitchenNotFoundException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public RestaurantModel update(@PathVariable Long id, @RequestBody @Valid RestaurantInput restaurantInput) {
//		Restaurant restaurant = restaurantInputDisassembler.toDomainObject(restaurantInput);
		
		Restaurant restaurant2 = restaurantRegistrationService.findOrFail(id);
			
//		BeanUtils.copyProperties(restaurant, restaurant2, "id", "paymentMethods", "address", "registrationDate", "products");
		restaurantInputDisassembler.copyToDomainObject(restaurantInput, restaurant2);
		
		try {
			return restaurantModelAssemebler.toModel(restaurantRegistrationService.save(restaurant2));					
		} catch (KitchenNotFoundException e) {
			throw new BusinessException(e.getMessage());
		}
	}

//	@PatchMapping("/{id}")
//	public RestaurantModel updatePartial(@PathVariable Long id, @RequestBody Map<String, Object> fields, HttpServletRequest request) {
//		Restaurant restaurant = restaurantRegistrationService.findOrFail(id);
//
//		merge(fields, restaurant, request);
//		validate(restaurant, "restaurant");
//
//		return update(id, restaurant);
//	}

//	private void validate(Restaurant restaurant, String objectName) {
//		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurant, objectName);
//		smartValidator.validate(restaurant, bindingResult);
//		
//		if (bindingResult.hasErrors()) {
//			throw new ValidationExceptiom(bindingResult);
//		}
//	}

//	private void merge(Map<String, Object> originFields, Restaurant targetRestaurant, HttpServletRequest request) {
//		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
//		
//		try {
//			ObjectMapper objectMapper = new ObjectMapper();
//			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
//			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
//			
//			Restaurant originRestaurant = objectMapper.convertValue(originFields, Restaurant.class);
//
//			originFields.forEach((propertieName, propertieValue) -> {
//				Field field = ReflectionUtils.findField(Restaurant.class, propertieName);
//				field.setAccessible(true);
//
//				Object valueNew = ReflectionUtils.getField(field, originRestaurant);
//				ReflectionUtils.setField(field, targetRestaurant, valueNew);
//			});
//		} catch (IllegalArgumentException e) {
//			Throwable rootCause = ExceptionUtils.getRootCause(e);
//			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
//		}
//	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long id) {
		restaurantRegistrationService.delete(id);
	}
	
}
