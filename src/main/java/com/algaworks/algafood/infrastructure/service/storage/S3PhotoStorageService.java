package com.algaworks.algafood.infrastructure.service.storage;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.PhotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class S3PhotoStorageService implements PhotoStorageService {

	@Autowired
	private AmazonS3 amazonS3;

	@Autowired
	private StorageProperties storageProperties;

	@Override
	public void store(NewPhoto newPhoto) {

		try {
			String filePath = getFilePath(newPhoto.getFileName());
			
			var objectMetadata = new ObjectMetadata();
			objectMetadata.setContentType(newPhoto.getContentType());
			
			var putObjectRequest = new PutObjectRequest(storageProperties.getS3().getBucket(), filePath,
					newPhoto.getInputStream(), objectMetadata)
				.withCannedAcl(CannedAccessControlList.PublicRead);
			
			amazonS3.putObject(putObjectRequest);
		} catch (Exception e) {
			throw new StorageException("Unable to upload file to Amazon S3.", e);
		}
		
	}

	private String getFilePath(String fileName) {
		return String.format("%s/%s", storageProperties.getS3().getPhotoDirectory(), fileName);
	}

	@Override
	public void remove(String fileName) {
		
	}

	@Override
	public InputStream recover(String fileName) {
		return null;
	}

}
