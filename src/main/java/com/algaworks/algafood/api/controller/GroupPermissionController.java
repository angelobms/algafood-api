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

import com.algaworks.algafood.api.assembler.PermissionModelAssembler;
import com.algaworks.algafood.api.model.PermissionModel;
import com.algaworks.algafood.domain.model.GroupSystem;
import com.algaworks.algafood.domain.service.GroupSystemRegistrationService;

@RestController
@RequestMapping(value = "/groups/{groupId}/permissions")
public class GroupPermissionController {
	
	@Autowired
	private GroupSystemRegistrationService groupSystemRegistration;
	
	@Autowired
	private PermissionModelAssembler permissionModelAssembler;
	
	@GetMapping
	public List<PermissionModel> listar(@PathVariable Long groupId) {
		GroupSystem group = groupSystemRegistration.findOrFail(groupId);
		
		return permissionModelAssembler.toCollectionModel(group.getPermissions());
	}
	
	@PutMapping("/{permissionId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associar(@PathVariable Long groupId, @PathVariable Long permissionId) {
		groupSystemRegistration.associatePermission(groupId, permissionId);
	}

	@DeleteMapping("/{permissionId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociar(@PathVariable Long groupId, @PathVariable Long permissionId) {
		groupSystemRegistration.disassociatePermission(groupId, permissionId);
	}
	
}
