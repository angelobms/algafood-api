package com.algaworks.algafood.infrastructure.service.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.PhotoStorageService;

public class LocalPhotoStorageService implements PhotoStorageService {
	
	@Autowired
	private StorageProperties storageProperties;

	@Override
	public void store(NewPhoto newPhoto) {
		try {
			Path filePath = getFilePath(newPhoto.getFileName());
			FileCopyUtils.copy(newPhoto.getInputStream(), Files.newOutputStream(filePath));
		} catch (Exception e) {
			throw new StorageException("Could not store file.", e);
		}
	}
	
	@Override
	public void remove(String fileName) {
		try {
			Path filePath = getFilePath(fileName);
			Files.deleteIfExists(filePath);
		} catch (IOException e) {
			throw new StorageException("Could not delete file.", e);
		}
		
	}
	
	@Override
	public PhotoRetrieved recover(String fileName) {		
		try {
			Path filePath = getFilePath(fileName);
			PhotoRetrieved photoRetrieved = PhotoRetrieved.builder()
					.inputStream(Files.newInputStream(filePath))
					.build();
			
			return photoRetrieved;
		} catch (IOException e) {
			throw new StorageException("Could not recover file.", e);
		}
	}
	
	private Path getFilePath(String fileName) {
		return storageProperties.getLocal().getFolderPhoto().resolve(Path.of(fileName));
	}
	
}
