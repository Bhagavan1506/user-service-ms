package com.javaexpress.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaexpress.dto.ChangePasswordDTO;
import com.javaexpress.dto.CredentialDto;
import com.javaexpress.service.CredentialServiceImpl;

import lombok.extern.slf4j.Slf4j;

// Spring Data REST 
@RestController
@RequestMapping("/api/credentials")
@Slf4j
public class CredentialController {

	@Autowired
	private CredentialServiceImpl credentialServiceImpl;
	

	// Mandatory to implement
	@GetMapping("username/{uname}")
	public ResponseEntity<CredentialDto> findByUsername(@PathVariable("uname") String username) {
		log.info("CredentialController findByUsername");
		CredentialDto result = credentialServiceImpl.findByUsername(username);
		return ResponseEntity.ok(result);
	}
	
	
	@PatchMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDTO dto) {
        String result = credentialServiceImpl.changePassword(dto);
        return ResponseEntity.ok(result);
    }
	
	
}
