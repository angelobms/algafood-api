package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.PaymentMethod;

public interface PaymentMethodRepository {
	
	List<PaymentMethod> list();
	PaymentMethod find(Long id);
	PaymentMethod save(PaymentMethod paymentMethod);
	void delete(PaymentMethod paymentMethod);

}
