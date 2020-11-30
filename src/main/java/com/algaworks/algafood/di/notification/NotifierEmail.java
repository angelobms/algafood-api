package com.algaworks.algafood.di.notification;

import com.algaworks.algafood.di.model.Client;

public class NotifierEmail implements Notifier {
	
	private boolean capsLock;
	private String serverHostSMTP;
	
	public NotifierEmail(String serverHostSMTP) {
		this.serverHostSMTP = serverHostSMTP;
		System.out.println("NotifierEmail");
	}

	@Override
	public void notify(Client client, String message) {
		if (this.capsLock) {
			message = message.toUpperCase();
		}
		
		System.out.printf("Notifying %s through email %s using SMTP %s: %s\n", 
				client.getName(), client.getEmail(), this.getServerHostSMTP(), message);
	}

	public void setCapsLock(boolean capsLock) {
		this.capsLock = capsLock;
	}
	
	public String getServerHostSMTP() {
		return serverHostSMTP;
	}
}
