package com.javaexpress.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import com.javaexpress.dto.UserDto;
import com.javaexpress.exception.UserNotFoundException;
import com.javaexpress.helper.UserMapper;
import com.javaexpress.model.Credential;
import com.javaexpress.model.User;
import com.javaexpress.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testSaveUser() {
        // Arrange
        UserDto userDto = new UserDto();
        User user = new User();
        Credential credential = new Credential();
        credential.setPassword("plainPassword");
        user.setCredential(credential);

        User savedUser = new User();
        savedUser.setUserId(1L);
        savedUser.setCredential(credential);

        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(savedUser);
        when(userMapper.toDto(savedUser)).thenReturn(userDto);

        // Act
        UserDto result = userService.save(userDto);

        // Assert
        assertNotNull(result);
        verify(passwordEncoder).encode("plainPassword");
        verify(userRepository).save(user);
    }

    @Test
    void testFindById_UserExists() {
        User user = new User();
        user.setUserId(1L);
        UserDto userDto = new UserDto();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto result = userService.findById(1L);
        assertNotNull(result);
    }

    @Test
    void testFindById_UserNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findById(99L));
    }


    @Test
    void testUpdate_UserExists() {
        UserDto userDto = new UserDto();
        User user = new User();
        user.setUserId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto result = userService.update(1L, userDto);
        assertNotNull(result);
    }

    @Test
    void testUpdate_UserNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.update(999L, new UserDto()));
    }

    @Test
    void testDeleteById_UserExists() {
        when(userRepository.existsById(1L)).thenReturn(true);
        userService.delteById(1L);
        verify(userRepository).deleteById(1L);
    }

    @Test
    void testDeleteById_UserNotFound() {
        when(userRepository.existsById(100L)).thenReturn(false);
        assertThrows(UserNotFoundException.class, () -> userService.delteById(100L));
    }

    @Test
    void testFindByUsername_UserFound() {
        User user = new User();
        UserDto userDto = new UserDto();

        when(userRepository.findByCredentialUsername("john")).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto result = userService.findByUsername("john");
        assertNotNull(result);
    }

    @Test
    void testFindByUsername_UserNotFound() {
        when(userRepository.findByCredentialUsername("notfound")).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.findByUsername("notfound"));
    }
}

