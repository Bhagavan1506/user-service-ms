package com.javaexpress.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaexpress.model.Credential;

public interface CredentialRepository extends JpaRepository<Credential,Long> {

	// DSL Methods
	// input : String
	// output : Credential -> Optional or List of Credential
	
	Optional<Credential> findByUsername(String username);
}
