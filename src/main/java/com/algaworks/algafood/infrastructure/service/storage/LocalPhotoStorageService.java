package com.algaworks.algafood.infrastructure.service.storage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.algaworks.algafood.domain.service.PhotoStorageService;

@Service
public class LocalPhotoStorageService implements PhotoStorageService {
	
	@Value("${algafood.storage.local.folder-photo}")
	private Path folderPhotos;

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
	public InputStream recover(String fileName) {		
		try {
			Path filePath = getFilePath(fileName);
			return Files.newInputStream(filePath);
		} catch (IOException e) {
			throw new StorageException("Could not recover file.", e);
		}
	}
	
	private Path getFilePath(String fileName) {
		return folderPhotos.resolve(Path.of(fileName)); 
	}
	
}
