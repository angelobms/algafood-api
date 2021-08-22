package com.algaworks.algafood.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityInput {

	@NotBlank
	private String name;
	
	@Valid
	@NotNull
	private StateIdInput state;
	
}
