package com.algaworks.algafood.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductPhotoModel {
	
	private String fileName;
	private String description;
	private String contentType;
	private Long size;
}
