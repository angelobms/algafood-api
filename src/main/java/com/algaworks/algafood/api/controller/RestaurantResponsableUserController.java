package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.UserSystemModelAssembler;
import com.algaworks.algafood.api.model.UserSystemModel;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.service.RestaurantRegistrationService;

@RestController
@RequestMapping(value = "/restaurants/{restaurantId}/responsables")
public class RestaurantResponsableUserController {
	
	@Autowired
	private RestaurantRegistrationService restaurantRegistration;
	
	@Autowired
    private UserSystemModelAssembler userSystemModelAssembler;
	
	@GetMapping
    public List<UserSystemModel> listar(@PathVariable Long restaurantId) {
        Restaurant restaurant = restaurantRegistration.findOrFail(restaurantId);
        
        return userSystemModelAssembler.toCollectionModel(restaurant.getResponsibles());
    }
	
	@PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long restaurantId, @PathVariable Long userId) {
    	restaurantRegistration.associateResponsable(restaurantId, userId);
    }
    
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long restaurantId, @PathVariable Long userId) {
        restaurantRegistration.disassociateResponsable(restaurantId, userId);
    }
    
}
