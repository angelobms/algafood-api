package com.algaworks.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.GroupSystemModel;
import com.algaworks.algafood.domain.model.GroupSystem;

@Component
public class GroupSystemModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public GroupSystemModel toModel(GroupSystem groupSystem) {
		return modelMapper.map(groupSystem, GroupSystemModel.class);
	}
	
	public List<GroupSystemModel> toColectionModel(Collection<GroupSystem> groupSystems) {
		return groupSystems.stream()
				.map(groupSystem -> toModel(groupSystem))
				.collect(Collectors.toList());
	}
	
}
