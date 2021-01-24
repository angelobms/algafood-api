package com.algaworks.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Kitchen;

public class ConsultKitchenMain {
	
	public static void main(String[] args) {
		
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		RegisterKitchen registerKitchen = applicationContext.getBean(RegisterKitchen.class);
		List<Kitchen> kitchens = registerKitchen.listar();
		
		for (Kitchen kitchen : kitchens) {
			System.out.println(kitchen.getName());
		}
		
	}

}
