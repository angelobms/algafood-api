package com.algaworks.algafood.domain.service;

import java.io.InputStream;

import lombok.Builder;
import lombok.Getter;

public interface PhotoStorageService {
	
	void store(NewPhoto newPhoto);
	
	@Builder
	@Getter	
	class NewPhoto {
		
		private String fileName;
		private InputStream inputStream;
		
	}

}
