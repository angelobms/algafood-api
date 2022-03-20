package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.GroupSystemInput;
import com.algaworks.algafood.domain.model.GroupSystem;

@Component
public class GroupSystemInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public GroupSystem toDomainObject(GroupSystemInput groupSystemInput) {
		return modelMapper.map(groupSystemInput, GroupSystem.class);
	}
	
	public void copyToDomainObject(GroupSystemInput groupSystemInput, GroupSystem groupSystem) {
		modelMapper.map(groupSystemInput, groupSystem);
	}

}
