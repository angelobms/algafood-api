package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.OrderInputDisassembler;
import com.algaworks.algafood.api.assembler.OrderRestaurantAssembler;
import com.algaworks.algafood.api.assembler.OrderSummaryAssembler;
import com.algaworks.algafood.api.model.OrderRestaurantModel;
import com.algaworks.algafood.api.model.OrderSummaryModel;
import com.algaworks.algafood.api.model.input.OrderInput;
import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.OrderRestaurant;
import com.algaworks.algafood.domain.model.UserSystem;
import com.algaworks.algafood.domain.repository.OrderRestaurantRepository;
import com.algaworks.algafood.domain.service.OrderIssuanceService;

@RestController
@RequestMapping(value = "/orders")
public class OrderRestaurantController {
	
	
	@Autowired
	private OrderRestaurantRepository orderRestaurantRepository;
	
	@Autowired
	private OrderIssuanceService orderIssuanceService;
	
	@Autowired
	private OrderRestaurantAssembler orderRestaurantAssembler;
	
	@Autowired
	private OrderSummaryAssembler orderSummaryAssembler;
	
	@Autowired
	private OrderInputDisassembler orderInputDisassembler; 
		
	@GetMapping
	public List<OrderSummaryModel> list() {
		List<OrderRestaurant> allOrders = orderRestaurantRepository.findAll();
		
		return orderSummaryAssembler.toCollectionModel(allOrders);
	}
	
	@GetMapping("/{xtid}")
	public OrderRestaurantModel find(@PathVariable String xtid) {
		OrderRestaurant orderRestaurant = orderIssuanceService.findOrFail(xtid);
		
		return orderRestaurantAssembler.toModel(orderRestaurant);
	}
	
	public OrderRestaurantModel add(@Valid @RequestBody OrderInput orderInput) {
		try {
			OrderRestaurant order = orderInputDisassembler.toDomainObject(orderInput);
			
			// TODO: get authenticated user
			order.setCustomer(new UserSystem());
			order.getCustomer().setId(1L);
			
			order = orderIssuanceService.emit(order);
			
			return orderRestaurantAssembler.toModel(order);			
		} catch (EntityNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

}
