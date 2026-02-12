package com.javaexpress.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.javaexpress.dto.ChangePasswordDTO;
import com.javaexpress.dto.CredentialDto;
import com.javaexpress.helper.CredentialMapper;
import com.javaexpress.model.Credential;
import com.javaexpress.repository.CredentialRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CredentialServiceImpl {

    private final CredentialRepository credentialRepository;
    private final CredentialMapper credentialMapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public CredentialDto findByUsername(String username) {
        log.info("CredentialService :: findByUsername({})", username);
        
        return credentialRepository.findByUsername(username)
                .map(credentialMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Username '" + username + "' not found in DB."));
    }
    
    
    public String changePassword(ChangePasswordDTO dto) {
        Credential credential = credentialRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(dto.getOldPassword(), credential.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        credential.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        credentialRepository.save(credential);
        return "Password changed successfully";
    }
}

