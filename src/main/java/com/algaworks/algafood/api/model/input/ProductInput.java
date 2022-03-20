package com.algaworks.algafood.api.model.input;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductInput {
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String description;
	
	@NotNull
	@PositiveOrZero
	private BigDecimal price;
	
	@NotNull
	private boolean active;

}
