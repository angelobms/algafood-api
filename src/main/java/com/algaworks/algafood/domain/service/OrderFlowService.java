package com.algaworks.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.model.OrderRestaurant;
import com.algaworks.algafood.domain.repository.OrderRestaurantRepository;

@Service
public class OrderFlowService {

	@Autowired
	private OrderIssuanceService orderFlowService;
	
	@Autowired
	private OrderRestaurantRepository orderRestaurantRepository;

	@Transactional
	public void confirm(String xtid) {
		OrderRestaurant order = orderFlowService.findOrFail(xtid);		
		order.confirm();
		
		orderRestaurantRepository.save(order);
	}
	
	@Transactional
	public void cancel(String xtid) {
		OrderRestaurant order = orderFlowService.findOrFail(xtid);
		order.cancel();
		
		orderRestaurantRepository.save(order);
	}
	
	@Transactional
	public void deliver(String xtid) {
		OrderRestaurant order = orderFlowService.findOrFail(xtid);
		order.delivery();
	}

}
