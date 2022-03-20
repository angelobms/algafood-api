package com.algaworks.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.UserSystemModel;
import com.algaworks.algafood.domain.model.UserSystem;

@Component
public class UserSystemModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public UserSystemModel toModel(UserSystem userSystem) {
		return modelMapper.map(userSystem, UserSystemModel.class);
	}
	
	public List<UserSystemModel> toCollectionModel(Collection<UserSystem> userSystems) {
		return userSystems.stream()
				.map(userSystem -> toModel(userSystem))
				.collect(Collectors.toList());
	}
	
}
