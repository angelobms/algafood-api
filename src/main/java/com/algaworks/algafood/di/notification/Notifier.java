package com.algaworks.algafood.di.notification;

import com.algaworks.algafood.di.model.Client;

public interface Notifier {

	void notify(Client client, String message);

}