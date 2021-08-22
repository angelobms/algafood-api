package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.StateModel;
import com.algaworks.algafood.domain.model.State;

@Component
public class StateModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public StateModel toModel(State state) {
		return modelMapper.map(state, StateModel.class);
	}
	
	public List<StateModel> toCollectionModel(List<State> states) {
		return states.stream()
				.map(state -> toModel(state))
				.collect(Collectors.toList());
	}

}
