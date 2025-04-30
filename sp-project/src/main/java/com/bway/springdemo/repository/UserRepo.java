package com.bway.springdemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bway.springdemo.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{
	
	User findByUsernameAndPassword(String un, String pass);
	User findByUsername(String username);
	User findByEmail(String email);	
	List<User> findByFnameContainingIgnoreCaseOrLnameContainingIgnoreCaseOrEmailContainingIgnoreCase(String fname, String lname, String email);

}
