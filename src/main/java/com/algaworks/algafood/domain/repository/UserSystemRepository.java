package com.algaworks.algafood.domain.repository;

import java.util.Optional;

import com.algaworks.algafood.domain.model.UserSystem;

public interface UserSystemRepository extends CustomJpaRepository<UserSystem, Long> {
	
	Optional<UserSystem> findByEmail(String email);

}
