package com.algaworks.algafood.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import com.algaworks.algafood.core.validation.Groups;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class City {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
	@NotBlank
	@Column(nullable = false)
	private String name;
	
	@Valid
	@ConvertGroup(from = Default.class, to = Groups.StateId.class)
	@NotNull	
	@ManyToOne
	@JoinColumn(name = "state_id", nullable = false)
	private State state;
}
