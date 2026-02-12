package com.javaexpress.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.javaexpress.dto.UserDto;
import com.javaexpress.exception.UserNotFoundException;
import com.javaexpress.helper.UserMapper;
import com.javaexpress.model.Credential;
import com.javaexpress.model.User;
import com.javaexpress.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserMapper userMapper;

	@Override
	public UserDto save(UserDto userDto) {

		User user = userMapper.toEntity(userDto);
		Credential credential = user.getCredential();

		// Secure the password
		String encodedPassword = passwordEncoder.encode(credential.getPassword());
		credential.setPassword(encodedPassword);

		// Set bidirectional mapping
		credential.setUser(user);
		user.setCredential(credential);

		// Save user (cascade saves credential)
		User savedUser = userRepository.save(user);

		return userMapper.toDto(savedUser);
	}

	@Override
	public UserDto findById(Long userId) {
		return userRepository.findById(userId).map(userMapper::toDto)
				.orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found."));
	}

	@Override
	public List<UserDto> findAll() {
		return userRepository.findAll().stream().sorted(Comparator.comparing(User::getUserId)).map(userMapper::toDto)
				.collect(Collectors.toList());
	}

	@Override
	public UserDto update(Long userId, UserDto userDto) {
		User dbUser = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("Cannot update. User with ID " + userId + " not found."));

		BeanUtils.copyProperties(userDto, dbUser, "credential"); // Exclude credential to avoid conflict
		User updatedUser = userRepository.save(dbUser);

		return userMapper.toDto(updatedUser);
	}

	@Override
	public void delteById(Long userId) {
		if (!userRepository.existsById(userId)) {
			throw new UserNotFoundException("Cannot delete. User with ID " + userId + " does not exist.");
		}
		userRepository.deleteById(userId);
	}

	@Override
	public UserDto findByUsername(String username) {
		log.info("UserService :: findByUsername({})", username);
		return userRepository.findByCredentialUsername(username).map(userMapper::toDto)
				.orElseThrow(() -> new UserNotFoundException("User with username '" + username + "' not found."));
	}
	
	public void showUsersWithCredentials() {
	    List<User> users = userRepository.findAll(); // 1 query

	    for (User user : users) {                    // N queries here
	        System.out.println(user.getCredential().getUsername());
	    }
	}
	
	
	public UserDto updateUser(Long userId, UserDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        
        userRepository.save(user);

        return userMapper.toDto(user);
    }
}
