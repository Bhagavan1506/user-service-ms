package com.javaexpress.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaexpress.dto.UserDto;
import com.javaexpress.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/users")
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;

	// Mandatory to implement
	@PostMapping
	public ResponseEntity<UserDto> save(@RequestBody UserDto userDto) {
		log.info("UserController :: save ");
		UserDto dbUser = userService.save(userDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(dbUser);
	}

	// Mandatory to implement
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> findById(@PathVariable Long userId) {
		log.info("UserController :: fetch user details");
		return ResponseEntity.ok(userService.findById(userId));
	}

//	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> update(@PathVariable Long userId, @RequestBody UserDto userDto) {
		log.info("UserController :: update");
		return ResponseEntity.ok(userService.update(userId, userDto));
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<Boolean> delete(@PathVariable Long userId) {
		log.info("UserController :: Delete");
		userService.delteById(userId);
		return ResponseEntity.ok(true);
	}

	@GetMapping
	public List<UserDto> getAllUsers() {
		return userService.findAll();
	}

	@GetMapping("/username/{username}")
	public UserDto getUserByUsername(@PathVariable String username) {
		return userService.findByUsername(username);
	}

	@PutMapping("/{userId}")
	public UserDto updateUser(@PathVariable Long userId, @RequestBody UserDto dto) {
		return userService.updateUser(userId, dto);
	}
}
