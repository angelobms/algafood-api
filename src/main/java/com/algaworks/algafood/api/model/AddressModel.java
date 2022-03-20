package com.algaworks.algafood.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressModel {
	
	private String cep;
	private String street;
	private String number;
	private String complement;
	private String district;
	private CitySummaryModel city;

}
