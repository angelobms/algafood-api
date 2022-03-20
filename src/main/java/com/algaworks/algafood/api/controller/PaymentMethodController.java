package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.PaymentMethodModelAssembler;
import com.algaworks.algafood.api.assembler.PaymentMethodInputDisassembler;
import com.algaworks.algafood.api.model.PaymentMethodModel;
import com.algaworks.algafood.api.model.input.PaymentMethodInput;
import com.algaworks.algafood.domain.model.PaymentMethod;
import com.algaworks.algafood.domain.repository.PaymentMethodRepository;
import com.algaworks.algafood.domain.service.PaymentMethodRegistrationService;

@RestController
@RequestMapping("payment-method")
public class PaymentMethodController {
	
	@Autowired
	private PaymentMethodRepository paymentMethodRepository;
	
	@Autowired
	private PaymentMethodRegistrationService paymentMethodRegistrationService;
	
	@Autowired
	private PaymentMethodModelAssembler paymentMethodAssembler;
	
	@Autowired
	private PaymentMethodInputDisassembler paymentMethodInputDisassembler;
	
	@GetMapping
    public List<PaymentMethodModel> list() {
        List<PaymentMethod> paymentMethods = paymentMethodRepository.findAll();
        
        return paymentMethodAssembler.toCollectionModel(paymentMethods);
    }
    
    @GetMapping("/{paymentMethodId}")
    public PaymentMethodModel find(@PathVariable Long paymentMethodId) {
        PaymentMethod paymentMethod = paymentMethodRegistrationService.findOrFail(paymentMethodId);
        
        return paymentMethodAssembler.toModel(paymentMethod);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentMethodModel add(@RequestBody @Valid PaymentMethodInput paymentMethodInput) {
    	PaymentMethod paymentMethod = paymentMethodInputDisassembler.toDomainObject(paymentMethodInput);
        
    	paymentMethod = paymentMethodRegistrationService.save(paymentMethod);
        
        return paymentMethodAssembler.toModel(paymentMethod);
    }
    
    @PutMapping("/{paymentMethodId}")
    public PaymentMethodModel update(@PathVariable Long paymentMethodId,
            @RequestBody @Valid PaymentMethodInput paymentMethodInput) {
        PaymentMethod paymentMethod = paymentMethodRegistrationService.findOrFail(paymentMethodId);
        
        paymentMethodInputDisassembler.copyToDomainObject(paymentMethodInput, paymentMethod);
        
        paymentMethod = paymentMethodRegistrationService.save(paymentMethod);
        
        return paymentMethodAssembler.toModel(paymentMethod);
    }
    
    @DeleteMapping("/{paymentMethodId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long paymentMethodId) {
        paymentMethodRegistrationService.delete(paymentMethodId);	
    }   
}                
