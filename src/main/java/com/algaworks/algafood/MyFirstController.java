package com.algaworks.algafood;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.algaworks.algafood.di.model.Client;
import com.algaworks.algafood.di.service.ClientActivationService;

@Controller
public class MyFirstController {
	
	private ClientActivationService clientActivationService;
	
	public MyFirstController(ClientActivationService clientActivationService) {
		this.clientActivationService = clientActivationService;
		
		System.out.println("MyFirstController: " + clientActivationService);
	}

	@GetMapping("/hello")
	@ResponseBody
	public String hello() {
		Client angelo = new Client("Angelo", "angelobms@gmail.com", "71988546321");
		
		clientActivationService.activate(angelo);
		return "Hello";
	}

}
