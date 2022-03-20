package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.GroupSystemModelAssembler;
import com.algaworks.algafood.api.model.GroupSystemModel;
import com.algaworks.algafood.domain.model.UserSystem;
import com.algaworks.algafood.domain.service.UserSystemRegistrationService;

@RestController
@RequestMapping("/users/{userId}/groups")
public class UserGroupController {
	
	@Autowired
	private UserSystemRegistrationService userSystemRegistration;
	
	@Autowired
	private GroupSystemModelAssembler groupSystemModelAssembler;
	
	@GetMapping
	public List<GroupSystemModel> list(@PathVariable Long userId) {
		UserSystem user = userSystemRegistration.findOrFail(userId);
		
		return groupSystemModelAssembler.toColectionModel(user.getGroups());
	}
	
    @PutMapping("/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associate(@PathVariable Long userId, @PathVariable Long groupId) {
    	userSystemRegistration.associateGroup(userId, groupId);
    }        
	
    @DeleteMapping("/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disassociate(@PathVariable Long userId, @PathVariable Long groupId) {
		userSystemRegistration.disassociateGroup(userId, groupId);
    }

}
