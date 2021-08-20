package com.algaworks.algafood;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.KitchenNotFoundException;
import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.service.KitchenRegistrationService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class KitchenRegistrationTest {

	@Autowired
	private KitchenRegistrationService kitchenRegistrationService;

	@Test
	public void shouldKitchenRegistrationWithSuccess() {

		// Give
		Kitchen newKitchen = new Kitchen();
		newKitchen.setName("Chinese");

		// When
		newKitchen = kitchenRegistrationService.save(newKitchen);

		// Then
		assertThat(newKitchen).isNotNull();
		assertThat(newKitchen.getId()).isNotNull();
	}

	@Test
	public void shouldFailkitchenRegistrationWhenWithoutName() {

		// Give
		Kitchen newKitchen = new Kitchen();
		newKitchen.setName(null);

		// When
		ConstraintViolationException exceptionExpected = Assertions.assertThrows(ConstraintViolationException.class,
				() -> {
					kitchenRegistrationService.save(newKitchen);
				});

		// Then
		assertThat(exceptionExpected).isNotNull();
	}

	@Test
	public void shoulfFailWhenDeleteKitchenInUse() {

		// Give that kitchen in use

		// When
		EntityInUseException exceptionExpected = Assertions.assertThrows(EntityInUseException.class, () -> {
			kitchenRegistrationService.delete(1L);
		});

		// Then
		assertThat(exceptionExpected).isNotNull();
	}

	@Test
	public void shouldFailWhenDeleteKitchenNonexistent() {

		// Give that kitchen in use

		// When
		KitchenNotFoundException exceptionExpected = Assertions.assertThrows(KitchenNotFoundException.class, () -> {
			kitchenRegistrationService.delete(100L);
		});

		// Then
		assertThat(exceptionExpected).isNotNull();
	}

}
