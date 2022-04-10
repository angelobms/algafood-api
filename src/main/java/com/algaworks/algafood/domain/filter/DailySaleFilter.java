package com.algaworks.algafood.domain.filter;

import java.time.OffsetDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DailySaleFilter {
	
	private Long restaurantId;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime startRegistrationDate;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime endRegistrationDate;

}
