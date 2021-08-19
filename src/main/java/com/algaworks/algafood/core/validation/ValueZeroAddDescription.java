package com.algaworks.algafood.core.validation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = { ValueZeroAddDescriptionValidator.class })
public @interface ValueZeroAddDescription {
	
	String message() default "Mandatory description invalid";
	
	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
	
	String valueField();
	
	String descriptionField();
	
	String mandatoryDescription();

}
