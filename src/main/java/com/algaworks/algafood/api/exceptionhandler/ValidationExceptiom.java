package com.algaworks.algafood.api.exceptionhandler;

import org.springframework.validation.BindingResult;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ValidationExceptiom extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private BindingResult bindingResult;
	
}
