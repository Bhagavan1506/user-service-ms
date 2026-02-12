package com.javaexpress.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.javaexpress.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	// fetch userdetails by username
	// input username
	// ouput User
	
	Optional<User> findByCredentialUsername(String username);
	
	//@Query(name="select username,firstName from user",nativeQuery = true)
	//List<Object[]> fetchUserInformation(Integer id);
	
	
	@EntityGraph(attributePaths = "credential")
    List<User> findAll(); // Solves N+1 problem
	
	@Query("SELECT u FROM User u JOIN FETCH u.credential")
	List<User> fetchAllUsersWithCredentials();
}
