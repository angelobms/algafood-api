package com.algaworks.algafood.infrastructure.service.storage;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.PhotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

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

	@Override
	public void remove(String fileName) {
		try {
			String filePath = getFilePath(fileName);
			
			var deleteObjectRequest = new DeleteObjectRequest(storageProperties.getS3().getBucket(), filePath);
			
			amazonS3.deleteObject(deleteObjectRequest);
		} catch (Exception e) {
			throw new StorageException("Unable to delete file in Amazon S3.", e);
		}
	}

	@Override
	public PhotoRetrieved recover(String fileName) {
		
		String filePath = getFilePath(fileName);
		
		URL url = amazonS3.getUrl(storageProperties.getS3().getBucket(), filePath);
		
		return PhotoRetrieved.builder()
				.url(url.toString()).build();
	}
	
	private String getFilePath(String fileName) {
		return String.format("%s/%s", storageProperties.getS3().getPhotoDirectory(), fileName);
	}

}
