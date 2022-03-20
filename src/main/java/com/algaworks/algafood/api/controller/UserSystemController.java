package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.UserSystemInputDisassembler;
import com.algaworks.algafood.api.assembler.UserSystemModelAssembler;
import com.algaworks.algafood.api.model.UserSystemModel;
import com.algaworks.algafood.api.model.input.PasswordInput;
import com.algaworks.algafood.api.model.input.UserSystemInput;
import com.algaworks.algafood.api.model.input.UserWithPasswordInput;
import com.algaworks.algafood.domain.model.UserSystem;
import com.algaworks.algafood.domain.repository.UserSystemRepository;
import com.algaworks.algafood.domain.service.UserSystemRegistrationService;

@RestController
@RequestMapping("/users")
public class UserSystemController {
	
	@Autowired
	private UserSystemRepository userSystemRepository;
	
	@Autowired
	private UserSystemRegistrationService userSystemRegistrationService;
	
	@Autowired
	private UserSystemModelAssembler userSystemModelAssembler;
	
	@Autowired
	private UserSystemInputDisassembler userSystemInputDisassembler;
	
	@GetMapping
	public List<UserSystemModel> list() {
		List<UserSystem> userSystems = userSystemRepository.findAll();
		
		return userSystemModelAssembler.toCollectionModel(userSystems);
	}
	
	@GetMapping("/{userSystemId}")
	public UserSystemModel find(@PathVariable("userSystemId") Long userSystemId) {
		UserSystem userSystem = userSystemRegistrationService.findOrFail(userSystemId);
		
		return userSystemModelAssembler.toModel(userSystem);
	}
	
	@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserSystemModel add(@RequestBody @Valid UserWithPasswordInput userInput) {
        UserSystem user = userSystemInputDisassembler.toDomainObject(userInput);
        user = userSystemRegistrationService.save(user);
        
        return userSystemModelAssembler.toModel(user);
    }
    
    @PutMapping("/{userId}")
    public UserSystemModel update(@PathVariable Long userId,
            @RequestBody @Valid UserSystemInput userInput) {
        UserSystem userCurrent = userSystemRegistrationService.findOrFail(userId);
        userSystemInputDisassembler.copyToDomainObject(userInput, userCurrent);
        userCurrent = userSystemRegistrationService.save(userCurrent);
        
        return userSystemModelAssembler.toModel(userCurrent);
    }
    
    @PutMapping("/{userId}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@PathVariable Long userId, @RequestBody @Valid PasswordInput password) {
        userSystemRegistrationService.updatePassword(userId, password.getCurrentPassword(), password.getNewPassword());
    }            

}
