package com.javaexpress.dto;

import com.javaexpress.model.RoleBasedAuthority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CredentialDto {

	private Long credentialId;
	private String username;
	private String password;
	private RoleBasedAuthority roleBasedAuthority;
	private Boolean isEnabled;
	private Boolean isAccountNonExpired;
	private Boolean isAccountNonLocked;
	private Boolean isCredentailsNonExpired;
	
	//@JsonProperty("user")
	//private UserDto userDto;
}
