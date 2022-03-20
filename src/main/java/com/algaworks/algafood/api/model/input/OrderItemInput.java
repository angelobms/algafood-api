package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemInput {
	
	@NotNull
	private Long productId;
	
	@NotNull
    @PositiveOrZero
	private Long amount;
	
	private String note;

}
