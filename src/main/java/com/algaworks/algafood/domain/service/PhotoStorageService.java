package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface PhotoStorageService {
	
	void store(NewPhoto newPhoto);
	
	void remove(String fileName);
	
	PhotoRetrieved recover(String fileName);
	
	default void replace(String oldFileName, NewPhoto newPhoto) {
		this.store(newPhoto);
		if (oldFileName != null) {
			this.remove(oldFileName);
		}
	}
	
	default String createFileName(String fileName) {
		return UUID.randomUUID().toString() + "_" + fileName;
	}
	
	@Builder
	@Getter	
	class NewPhoto {
		private String fileName;
		private String contentType;
		private InputStream inputStream;
	}
	
	@Builder
	@Getter	
	class PhotoRetrieved {
		private InputStream inputStream;
		private String url;
		
		public boolean hasInputStream() {
			return inputStream != null;
		}
		
		public boolean hasUrl() {
			return url != null;
		}
	}

}
