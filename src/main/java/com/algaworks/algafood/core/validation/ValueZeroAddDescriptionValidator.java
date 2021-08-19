package com.algaworks.algafood.core.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;

import org.springframework.beans.BeanUtils;

public class ValueZeroAddDescriptionValidator implements ConstraintValidator<ValueZeroAddDescription, Object> {

	private String valueField;
	private String descriptionField;
	private String mandatoryDescription;
	
	@Override
	public void initialize(ValueZeroAddDescription constraintAnnotation) {
		this.valueField = constraintAnnotation.valueField();
		this.descriptionField = constraintAnnotation.descriptionField();
		this.mandatoryDescription = constraintAnnotation.mandatoryDescription();
	}
	
	@Override
	public boolean isValid(Object objectValue, ConstraintValidatorContext context) {
		boolean valid = true;
		
		try {
			BigDecimal value = (BigDecimal) BeanUtils.getPropertyDescriptor(objectValue.getClass(), valueField).getReadMethod().invoke(objectValue);
			
			String description = (String) BeanUtils.getPropertyDescriptor(objectValue.getClass(), descriptionField).getReadMethod().invoke(objectValue);
			
			if (value != null && BigDecimal.ZERO.compareTo(value) == 0 && description != null) {
				valid = description.toLowerCase().contains(this.mandatoryDescription.toLowerCase());
			}
			
			return valid;
		} catch (Exception e) {
			throw new ValidationException(e);
		}
	}
	
}
