package com.javaexpress.helper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.javaexpress.dto.UserDto;
import com.javaexpress.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
	//@Mapping(source = "email", target = "emailAddress")
	//@Mapping(target = "password", ignore = true)
//	   @Mappings({
//	        @Mapping(source = "credentialId", target = "credential.credentialId"),
//	        @Mapping(source = "username", target = "credential.username"),
//	        @Mapping(source = "password", target = "credential.password"),
//	        @Mapping(source = "roleBasedAuthority", target = "credential.roleBasedAuthority")
//	    })
    UserDto toDto(User user);
	
    User toEntity(UserDto userDto);
}

