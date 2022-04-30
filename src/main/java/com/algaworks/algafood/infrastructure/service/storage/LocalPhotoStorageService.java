package com.algaworks.algafood.infrastructure.service.storage;

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
	
	private Path getFilePath(String fileName) {
		return folderPhotos.resolve(Path.of(fileName)); 
	}

}
