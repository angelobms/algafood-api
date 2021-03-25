package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Permission;
import com.algaworks.algafood.domain.repository.PermissionRepository;

@Component
public class PermissionRepositoryImpl implements PermissionRepository {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Permission> list() {
		return manager.createQuery("from Permission", Permission.class).getResultList();
	}

	@Override
	public Permission find(Long id) {
		return manager.find(Permission.class, id);
	}

	@Transactional
	@Override
	public Permission save(Permission permission) {
		return manager.merge(permission);
	}

	@Transactional
	@Override
	public void delete(Permission permission) {
		permission = find(permission.getId());
		manager.remove(permission);
	}

}
