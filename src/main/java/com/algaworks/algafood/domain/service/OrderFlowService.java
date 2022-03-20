package com.algaworks.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.model.OrderRestaurant;

@Service
public class OrderFlowService {

	@Autowired
	private OrderIssuanceService orderFlowService;

	@Transactional
	public void confirm(String xtid) {
		OrderRestaurant order = orderFlowService.findOrFail(xtid);		
		order.confirm();
	}
	
	@Transactional
	public void cancel(String xtid) {
		OrderRestaurant order = orderFlowService.findOrFail(xtid);
		order.cancel();
	}
	
	@Transactional
	public void deliver(String xtid) {
		OrderRestaurant order = orderFlowService.findOrFail(xtid);
		order.delivery();
	}

}
