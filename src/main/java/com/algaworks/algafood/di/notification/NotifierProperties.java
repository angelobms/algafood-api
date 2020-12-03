package com.algaworks.algafood.di.notification;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("notifier.email")
public class NotifierProperties {
	
	/**
	 * Email server host
	 */
	private String hostServer;
	
	/**
	 * Email server port
	 */	
	private Integer portServer = 25;
	
	public String getHostServer() {
		return hostServer;
	}
	
	public void setHostServer(String hostServer) {
		this.hostServer = hostServer;
	}
	
	public Integer getPortServer() {
		return portServer;
	}
	
	public void setPortServer(Integer portServer) {
		this.portServer = portServer;
	}

}
