package com.algaworks.algafood.core.jackson;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.module.SimpleModule;

@Component
public class JacksonMixinModule extends SimpleModule {

	private static final long serialVersionUID = 1L;
	
	public JacksonMixinModule() {
		//setMixInAnnotation(Restaurant.class, RestaurantMixin.class);
		//setMixInAnnotation(Kitchen.class, KitchenMixin.class);
		//setMixInAnnotation(City.class, CityMixin.class);
	}

}
