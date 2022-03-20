package com.algaworks.algafood.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRestaurantModel {

	private String xtid;
    private BigDecimal subTotal;
    private BigDecimal freightRate;
    private BigDecimal totalValue;
    private String status;
    private OffsetDateTime registrationDate;
    private OffsetDateTime confirmationDate;
    private OffsetDateTime deliveryDate;
    private OffsetDateTime cancellationDate;
    private RestaurantSummaryModel restaurant;
    private UserSystemModel customer;
    private PaymentMethodModel paymentMethod;
    private AddressModel addressDelivery;
    private List<OrderItemModel> itens;   
	
}
