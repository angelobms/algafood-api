package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.GroupSystemNotFounException;
import com.algaworks.algafood.domain.model.GroupSystem;
import com.algaworks.algafood.domain.model.Permission;
import com.algaworks.algafood.domain.repository.GroupSystemRepository;

@Service
public class GroupSystemRegistrationService {

	private static final String MSG_GROUP_SYSTEM_IN_USE = "Group system for code %d cannot be removed as it is in use";
	
	@Autowired
	private GroupSystemRepository groupSystemRepository;
	
	@Autowired
	private PermissionRegistrationService permissionRegistration; 
	
	@Transactional
	public GroupSystem save(GroupSystem groupSystem) {
		return groupSystemRepository.save(groupSystem);
	}
	
	@Transactional
	public void delete(Long groupSystemId) {
		try {
			groupSystemRepository.deleteById(groupSystemId);
			groupSystemRepository.flush();
			
		} catch (EmptyResultDataAccessException e) {
			throw new GroupSystemNotFounException(groupSystemId);
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(
					String.format(MSG_GROUP_SYSTEM_IN_USE, groupSystemId));
		}
	}
	
	public GroupSystem findOrFail(Long groupSystemId) {
		return groupSystemRepository.findById(groupSystemId)
				.orElseThrow(() -> new GroupSystemNotFounException(groupSystemId));
	}
	
	@Transactional
	public void associatePermission(Long groupId, Long permissionId) {
		GroupSystem groupSystem = findOrFail(groupId);
		Permission permission = permissionRegistration.findOrFail(permissionId);
		
		groupSystem.addPermission(permission);
	}
	
	@Transactional
	public void disassociatePermission(Long groupId, Long permissionId) {
		GroupSystem groupSystem = findOrFail(groupId);
		Permission permission = permissionRegistration.findOrFail(permissionId);
		
		groupSystem.removePermission(permission);
	}

}
