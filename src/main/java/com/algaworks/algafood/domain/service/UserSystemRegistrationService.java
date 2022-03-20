package com.algaworks.algafood.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.UserSystemNotFoundException;
import com.algaworks.algafood.domain.model.GroupSystem;
import com.algaworks.algafood.domain.model.UserSystem;
import com.algaworks.algafood.domain.repository.UserSystemRepository;

@Service
public class UserSystemRegistrationService {
	
	@Autowired
	private UserSystemRepository userSystemRepository;	
	
	@Autowired
	private GroupSystemRegistrationService groupSystemRegistration; 
	
	@Transactional
	public UserSystem save(UserSystem userSystem) {
		
		Optional<UserSystem> existingUser = userSystemRepository.findByEmail(userSystem.getEmail());
		
		if (existingUser.isPresent() && !existingUser.get().equals(userSystem)) {
			throw new BusinessException(
					String.format("There is already a user registered with the email", userSystem.getEmail()));
		}
		
		return userSystemRepository.save(userSystem);
	}
	
	public void updatePassword(Long userSystemId, String currentpassword, String newPassword) {
		UserSystem userSystem = findOrFail(userSystemId);
		
		if (userSystem.passwordNotMatches(currentpassword)) {
			throw new BusinessException("Current password entered does not match user password.");
		}
		
		userSystem.setPassword(newPassword);
	}

	public UserSystem findOrFail(Long userSystemId) {
		return userSystemRepository.findById(userSystemId)
				.orElseThrow(() -> new UserSystemNotFoundException(userSystemId));
	}

	@Transactional
	public void associateGroup(Long userId, Long groupId) {
	    UserSystem user = findOrFail(userId);
	    GroupSystem group = groupSystemRegistration.findOrFail(groupId);
	    
	    user.addGroup(group);
	}
	
	@Transactional
	public void disassociateGroup(Long userId, Long groupId) {
		UserSystem user = findOrFail(userId);
	    GroupSystem group = groupSystemRegistration.findOrFail(groupId);
	    
	    user.renoveGroup(group);
	}
	
}
