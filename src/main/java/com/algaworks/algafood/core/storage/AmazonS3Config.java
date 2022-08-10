package com.algaworks.algafood.core.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AmazonS3Config {

	@Autowired
	private StorageProperties storageProperties;

    @Bean
    AmazonS3 amazonS3() {
        var credentials = new BasicAWSCredentials(storageProperties.getS3().getAccessKeyId(),
                storageProperties.getS3().getSecretAccessKey());
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(storageProperties.getS3().getRegion())                
                .build();
    }

}
