package com.javaexpress.service;

import java.util.List;

import com.javaexpress.dto.UserDto;

public interface UserService {

	UserDto save(UserDto userDto);
	UserDto findById(Long userId);
	List<UserDto> findAll();
	UserDto update(Long userId,UserDto userDto);
	void delteById(Long userId);
	UserDto findByUsername(String username);
	public UserDto updateUser(Long userId, UserDto dto);
}
