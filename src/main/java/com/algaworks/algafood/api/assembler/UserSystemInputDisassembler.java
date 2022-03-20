package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.UserSystemInput;
import com.algaworks.algafood.domain.model.UserSystem;

@Component
public class UserSystemInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public UserSystem toDomainObject(UserSystemInput userSystemInput) {
		return modelMapper.map(userSystemInput, UserSystem.class);
	}
	
	public void copyToDomainObject(UserSystemInput userSystemInput, UserSystem userSystem) {
		modelMapper.map(userSystemInput, userSystem);
	}
	
}
