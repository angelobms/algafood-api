package com.algaworks.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.PaymentMethod;
import com.algaworks.algafood.domain.repository.PaymentMethodRepository;

public class ConsultPaymentMethodMain {
	
	public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
        
        PaymentMethodRepository paymentMethodRepository = applicationContext.getBean(PaymentMethodRepository.class);
        
        List<PaymentMethod> allPaymentMethods = paymentMethodRepository.list();
        
        for (PaymentMethod paymentMethod: allPaymentMethods) {
            System.out.println(paymentMethod.getDescription());
        }
    }

}
