package com.algaworks.algafood.domain.model;

import java.util.Arrays;
import java.util.List;

public enum StatusOrder {
	
	CRIETED("Crieted"),
	CONFIRMED("Confirmed", CRIETED),
	DELIVERED("Delivered", CONFIRMED),
	CANCELED("Canceled", CRIETED);
	
	private String description;
	private List<StatusOrder> statusPrevious;
	
	StatusOrder(String description, StatusOrder... statusPrevious) {
		this.description = description;
		this.statusPrevious = Arrays.asList(statusPrevious);
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public boolean canNotChangeTo(StatusOrder newStatus) {
		return !newStatus.statusPrevious.contains(this);
	}

}
