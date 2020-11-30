package com.algaworks.algafood.di.model;

public class Client {
	
	private String name;
	private String email;
	private String phone;
	private boolean active = false;
	
	public Client(String name, String email, String phone) {
		super();
		this.name = name;
		this.email = email;
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public boolean isActive() {
		return active;
	}
	
	public void activate() {
		this.active = true;
	}

}
