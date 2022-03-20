package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.GroupSystemModelAssembler;
import com.algaworks.algafood.api.assembler.GroupSystemInputDisassembler;
import com.algaworks.algafood.api.model.GroupSystemModel;
import com.algaworks.algafood.api.model.input.GroupSystemInput;
import com.algaworks.algafood.domain.model.GroupSystem;
import com.algaworks.algafood.domain.repository.GroupSystemRepository;
import com.algaworks.algafood.domain.service.GroupSystemRegistrationService;

@RestController
@RequestMapping("/groups")
public class GroupSystemController {
	
	@Autowired
	private GroupSystemRepository groupSystemRepository;
	
	@Autowired
	private GroupSystemRegistrationService groupSystemRegistrationService;
	
	@Autowired
	private GroupSystemModelAssembler groupSystemAssembler;
	
	@Autowired
	private GroupSystemInputDisassembler groupSystemDisassembler;
	
	@GetMapping
	public List<GroupSystemModel> list() {
		List<GroupSystem> groupSystemModels = groupSystemRepository.findAll();
		
		return groupSystemAssembler.toColectionModel(groupSystemModels);
	}
	
	@RequestMapping("/{groupId}")
	public GroupSystemModel find(@PathVariable("groupId") Long groupSysteId) {
		GroupSystem groupSystem = groupSystemRegistrationService.findOrFail(groupSysteId);
		
		return groupSystemAssembler.toModel(groupSystem);
	}
	
	@PutMapping("/{groupSysteId}")
	public GroupSystemModel update(@PathVariable Long groupId, @RequestBody @Valid GroupSystemInput groupSystemInput) {
		GroupSystem groupSystem = groupSystemRegistrationService.findOrFail(groupId);
		
		groupSystemDisassembler.copyToDomainObject(groupSystemInput, groupSystem);
		
		groupSystem = groupSystemRegistrationService.save(groupSystem);
		
		return groupSystemAssembler.toModel(groupSystem);
	}
	
	@DeleteMapping("/{groupId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(Long groupId) {
		groupSystemRegistrationService.delete(groupId);
	}

}
