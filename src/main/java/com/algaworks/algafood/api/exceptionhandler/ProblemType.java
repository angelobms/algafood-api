package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	
	SYSTEM_ERROR("System error", "/system-error"),
	INVALID_PARAMETER("Invalid parameter", "/invalid-parameter"),
	INCOMPREHENSIBLE_MESSAGE("Incomprehensible message","/incomprehensible-message"),
	RESOURCE_NOT_FOUND("Resource not found", "/resource-not-found"),
	ENTITY_IN_USE("Entity in use", "/entity-in-use"),
	ERRO_BUSINESS("Violation of business rule", "/erro-business"),
	INVALID_DATA("Invalid data", "/invalid-data");
	
	private String title;
	private String uri;
	
	private ProblemType(String title, String path) {
		this.title = title;
		this.uri = "https://algafood.com.br" + path;
	}

}
