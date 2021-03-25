package com.algaworks.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Permission;
import com.algaworks.algafood.domain.repository.PermissionRepository;

public class ConsultPermissionMain {
	
	public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
        
        PermissionRepository permissionRepository = applicationContext.getBean(PermissionRepository.class);
        
        List<Permission> allPermissions = permissionRepository.list();
        
        for (Permission permission: allPermissions) {
            System.out.printf("%s - %s\n", permission.getName(), permission.getDescription());
        }
    }
	
}
