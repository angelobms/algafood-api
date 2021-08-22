package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.CityInputDisassembler;
import com.algaworks.algafood.api.assembler.CityModelAssembler;
import com.algaworks.algafood.api.model.CityModel;
import com.algaworks.algafood.api.model.input.CityInput;
import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.StateNotFoundException;
import com.algaworks.algafood.domain.model.City;
import com.algaworks.algafood.domain.repository.CityRepositpry;
import com.algaworks.algafood.domain.service.CityRegistrationService;

@RestController
@RequestMapping("/cities")
public class CityController {
	
	@Autowired
	private CityRepositpry cityRepositpry;

	@Autowired
	private CityRegistrationService cityRegistrationService;
	
	@Autowired
	private CityModelAssembler cityModelAssembler;
	
	@Autowired
	private CityInputDisassembler cityInputDisassembler;
	
	@GetMapping
	public List<CityModel> list() {
		return cityModelAssembler.toCollectionModel(cityRepositpry.findAll());
	}
	
	@GetMapping("/{id}")
	public CityModel find(@PathVariable Long id) {
		return cityModelAssembler.toModel(cityRegistrationService.findOrFail(id));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CityModel add(@RequestBody @Valid CityInput cityInput) {
		try {
			City city = cityInputDisassembler.toDomainObject(cityInput);
			
			city = cityRegistrationService.save(city);
			
			return cityModelAssembler.toModel(city);
		} catch (StateNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{id}")
	public CityModel update(@PathVariable Long id, @Valid @RequestBody CityInput cityInput) {
		try {
			City city2 = cityRegistrationService.findOrFail(id);
			
//			BeanUtils.copyProperties(city, city2, "id");
			cityInputDisassembler.copyToDomainObject(cityInput, city2);
			
			city2 = cityRegistrationService.save(city2);
			
			return cityModelAssembler.toModel(city2); 		
		} catch (StateNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long id){		
		cityRegistrationService.delete(id);
	}
	
}
