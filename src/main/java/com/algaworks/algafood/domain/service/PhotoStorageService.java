package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface PhotoStorageService {
	
	void store(NewPhoto newPhoto);
	
	default String createFileName(String fileName) {
		return UUID.randomUUID().toString() + "_" + fileName;
	}
	
	@Builder
	@Getter	
	class NewPhoto {
		
		private String fileName;
		private InputStream inputStream;
		
	}

}
