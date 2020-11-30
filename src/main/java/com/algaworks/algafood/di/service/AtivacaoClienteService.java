package com.algaworks.algafood.di.service;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.modelo.Cliente;
import com.algaworks.algafood.di.notificacao.NotificadorEmail;

@Component
public class AtivacaoClienteService {
	
	private NotificadorEmail notificadorEmail;
	
	public void ativar(Cliente cliente) {
		cliente.ativar();
		
		notificadorEmail.notificar(cliente, "Seu cadastro no sistema está ativo!");
	}

}
