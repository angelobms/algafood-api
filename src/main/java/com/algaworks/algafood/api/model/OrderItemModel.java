package com.algaworks.algafood.api.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemModel {

	private Long productId;
    private String productNome;
    private Integer amount;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private String observation; 
}
