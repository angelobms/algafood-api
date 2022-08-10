package com.algaworks.algafood.infrastructure.service.storage;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.service.PhotoStorageService;
import com.amazonaws.services.s3.AmazonS3;

@Service
public class S3PhotoStorageService implements PhotoStorageService {
	
	@Autowired
	private AmazonS3 amazonS3;

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
