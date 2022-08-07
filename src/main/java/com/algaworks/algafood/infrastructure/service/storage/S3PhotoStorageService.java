package com.algaworks.algafood.infrastructure.service.storage;

import java.io.InputStream;

import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.service.PhotoStorageService;

@Service
public class S3PhotoStorageService implements PhotoStorageService {

	@Override
	public void store(NewPhoto newPhoto) {
		
	}

	@Override
	public void remove(String fileName) {
		
	}

	@Override
	public InputStream recover(String fileName) {
		return null;
	}

}
