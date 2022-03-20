package com.algaworks.algafood.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSummaryModel {
	
	private String xtid;
    private BigDecimal subTotal;
    private BigDecimal freightRate;
    private BigDecimal totalValue;
    private String status;
    private OffsetDateTime registrationDate;
    private RestaurantSummaryModel restaurant;
    private UserSystemModel customer;
       
}
